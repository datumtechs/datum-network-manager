package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.common.util.BatchExecuteUtil;
import com.platon.rosettanet.admin.dao.GlobalDataFileMapper;
import com.platon.rosettanet.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import com.platon.rosettanet.admin.grpc.client.MetaDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author liushuyu
 * @Date 2021/7/12 11:48
 * @Version
 * @Desc 全网数据刷新
 */

@Slf4j
@Component
public class GlobalDataRefreshTask {

    @Resource
    private GlobalDataFileMapper globalDataFileMapper;
    @Resource
    private GlobalMetaDataColumnMapper globalMetaDataColumnMapper;
    @Resource
    private MetaDataClient metaDataClient;
    protected static ArrayBlockingQueue<GlobalDataFileDetail> abq = new ArrayBlockingQueue<>(10000);

    /**
     * TODO 后续增加补偿和失败重试等机制
     */
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void task(){
        StopWatch stopWatch = new StopWatch("全网数据刷新计时");
        //### 1.获取全网数据，包括本组织数据
        stopWatch.start("1.获取全网数据，包括本组织数据");
        List<GlobalDataFileDetail> detailList = null;
        try{
            detailList = metaDataClient.getMetaDataDetailList();
        } catch (ApplicationException exception){
            log.info(exception.getErrorMsg());
            return;
        }
        stopWatch.stop();
        //### 2.将数据归类
        stopWatch.start("2.将数据归类");
        //2.1先获取所有已存在数据库中的fileId
        List<String> fileIdList = globalDataFileMapper.selectAllFileId();
        //需要更新的列表
        List<GlobalDataFileDetail> updateList = new ArrayList<>();
        //需要新增的列表
        List<GlobalDataFileDetail> addList = new ArrayList<>();
        //需要删除的fileId列表
        List<String> deleteList = new ArrayList<>(fileIdList);
        String localOrg = LocalOrgIdentityCache.getIdentityId();
        detailList.stream()
                .filter(detail -> {//先过滤掉本组织数据
                    if(localOrg.equals(detail.getIdentityId())){
                        try {
                            //将指定的元素插入此队列的尾部，如果该队列已满，则一直等到（阻塞）
                            abq.offer(detail,60, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            log.error("本地组织数据放入队列失败",e);
                        }
                        return false;
                    } else {
                        return true;
                    }
                })
                .forEach(detail -> {//数据归类 TODO 性能方面考虑
                    deleteList.remove(detail.getFileId());
                    if(fileIdList.contains(detail.getFileId())){//如果已存在数据库，则进行更新
                        detail.setRecUpdateTime(new Date());
                        updateList.add(detail);
                    } else {//不存在，则表示是新增
                        Date date = new Date();
                        detail.setRecCreateTime(date);
                        detail.setRecUpdateTime(date);
                        addList.add(detail);
                    }
                });
        stopWatch.stop();
        //### 3.入库
        //3.1批量更新
        stopWatch.start("3.1批量更新");
        batchUpdate(updateList);
        stopWatch.stop();
        //3.2批量新增
        stopWatch.start("3.2批量新增");
        batchAdd(addList);
        stopWatch.stop();
        //3.3批量删除
        stopWatch.start("3.3批量删除");
        batchDeleteByMetaDataId(deleteList);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

    }

    private void batchAdd(List<GlobalDataFileDetail> addList) {
        List<GlobalDataFile> localDataFileList = new ArrayList<>();
        List<GlobalMetaDataColumn> columnList = new ArrayList<>();
        addList.forEach(detail -> {
            localDataFileList.add(detail);
            columnList.addAll(detail.getMetaDataColumnList());
        });

        //批量新增
        BatchExecuteUtil.batchExecute(1000,localDataFileList,(tempList)->{
            globalDataFileMapper.batchAddSelective(tempList);
        });
        BatchExecuteUtil.batchExecute(1000,columnList,(tempList)->{
            globalMetaDataColumnMapper.batchAddSelective(tempList);
        });
    }

    private void batchUpdate(List<GlobalDataFileDetail> updateList) {
        List<GlobalDataFile> localDataFileList = new ArrayList<>();
        List<GlobalMetaDataColumn> columnList = new ArrayList<>();
        updateList.forEach(detail -> {
            localDataFileList.add(detail);
            columnList.addAll(detail.getMetaDataColumnList());
        });

        //批量更新
        BatchExecuteUtil.batchExecute(1000,localDataFileList,(tempList)->{
            globalDataFileMapper.batchUpdateByFileIdSelective(tempList);
        });
        BatchExecuteUtil.batchExecute(1000,columnList,(tempList)->{
            globalMetaDataColumnMapper.batchUpdateByFileIdSelective(tempList);
        });
    }

    private void batchDeleteByMetaDataId(List<String> deleteList){
        BatchExecuteUtil.batchExecute(1000,deleteList,(tempList)->{
            globalDataFileMapper.batchDeleteByMetaDataId(tempList);
            globalMetaDataColumnMapper.batchDeleteByFileId(tempList);
        });
    }



}
