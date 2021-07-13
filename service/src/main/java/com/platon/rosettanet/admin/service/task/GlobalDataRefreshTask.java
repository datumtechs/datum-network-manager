package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.util.BatchExecuteUtil;
import com.platon.rosettanet.admin.dao.GlobalDataFileMapper;
import com.platon.rosettanet.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import com.platon.rosettanet.admin.grpc.client.MetaDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/12 11:48
 * @Version
 * @Desc 全网数据刷新
 */

@Slf4j
//@Component
public class GlobalDataRefreshTask {

    @Resource
    private GlobalDataFileMapper globalDataFileMapper;
    @Resource
    private GlobalMetaDataColumnMapper globalMetaDataColumnMapper;
    @Resource
    private MetaDataClient metaDataClient;


    @Scheduled(fixedDelay = 10000)
    public void task(){
        StopWatch stopWatch = new StopWatch("全网数据刷新计时");
        //### 1.获取全网数据，包括本组织数据
        stopWatch.start("1.获取全网数据，包括本组织数据");
        List<GlobalDataFileDetail> detailList = metaDataClient.getMetaDataDetailList();
        stopWatch.stop();
        //### 2.将数据归类
        stopWatch.start("2.将数据归类");
        //2.1先获取所有已存在数据库中的metaDataId
        List<String> metaDataIdList = globalDataFileMapper.selectAllMetaDataId();
        //需要更新的列表
        List<GlobalDataFileDetail> updateList = new ArrayList<>();
        //需要新增的列表
        List<GlobalDataFileDetail> addList = new ArrayList<>();
        //需要删除的metaDataId列表
        List<String> deleteList = new ArrayList<>(metaDataIdList);
        String localOrg = LocalOrgIdentityCache.getIdentityId();
        detailList.stream()
                .filter(detail -> {//先过滤掉本组织数据
                    if(localOrg.equals(detail.getIdentityId())){
                        return false;
                    } else {
                        return true;
                    }
                })
                .forEach(detail -> {//数据归类 TODO 性能方面考虑
                    deleteList.remove(detail.getMetaDataId());
                    if(metaDataIdList.contains(detail.getMetaDataId())){//如果已存在数据库，则进行更新
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
            globalDataFileMapper.batchUpdateByMetaDataIdSelective(tempList);
        });
        BatchExecuteUtil.batchExecute(1000,columnList,(tempList)->{
            globalMetaDataColumnMapper.batchUpdateByMetaDataIdSelective(tempList);
        });
    }

    private void batchDeleteByMetaDataId(List<String> deleteList){
        BatchExecuteUtil.batchExecute(1000,deleteList,(tempList)->{
            globalDataFileMapper.batchDeleteByMetaDataId(tempList);
            globalMetaDataColumnMapper.batchDeleteByMetaDataId(tempList);
        });
    }



}
