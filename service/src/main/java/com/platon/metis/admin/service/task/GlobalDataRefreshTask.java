package com.platon.metis.admin.service.task;

import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.util.BatchExecuteUtil;
import com.platon.metis.admin.dao.GlobalDataFileMapper;
import com.platon.metis.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.metis.admin.dao.SyncCheckpointMapper;
import com.platon.metis.admin.dao.entity.GlobalDataFile;
import com.platon.metis.admin.dao.entity.GlobalMetaDataColumn;
import com.platon.metis.admin.grpc.client.MetaDataClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Configuration
public class GlobalDataRefreshTask {

    @Resource
    private GlobalDataFileMapper globalDataFileMapper;
    @Resource
    private GlobalMetaDataColumnMapper globalMetaDataColumnMapper;
    @Resource
    private MetaDataClient metaDataClient;

    @Resource
    private SyncCheckpointMapper syncCheckpointMapper;

    protected static ArrayBlockingQueue<GlobalDataFile> localDataFileQueueFetchedFromStorage = new ArrayBlockingQueue<>(10000);

    /**
     * TODO 后续增加补偿和失败重试等机制
     */
    //@Scheduled(fixedDelay = 10000)
    @Scheduled(fixedDelayString = "${GlobalDataRefreshTask.fixedDelay}")
    @Transactional
    public void task(){
        StopWatch stopWatch = new StopWatch("全网数据刷新计时");
        //### 1.获取全网数据，包括本组织数据
        stopWatch.start("1.获取全网数据，包括本组织数据");
        List<GlobalDataFile> globalDataFileList = null;
        try{
            globalDataFileList = metaDataClient.getGlobalMetaDataList();
        } catch (ApplicationException exception){
            log.info(exception.getErrorMsg());
            return;
        }

        log.info("globalDataFileList.size():{}", globalDataFileList.size());

        if(CollectionUtils.isEmpty(globalDataFileList)){
            return;
        }else{
            GlobalDataFile last = globalDataFileList.get(globalDataFileList.size()-1);
            //syncCheckpointMapper.updateMetadata(last.getRecUpdateTime())
        }


        stopWatch.stop();
        //### 2.将数据归类
        stopWatch.start("2.将数据归类");
        //2.1先获取所有已存在数据库中的fileId
        List<String> existMetaDataIdList = globalDataFileMapper.selectAllMetaDataId();
        //需要更新的列表
        List<GlobalDataFile> updateList = new ArrayList<>();
        //需要新增的列表
        List<GlobalDataFile> addList = new ArrayList<>();
        String localIdentityId = LocalOrgIdentityCache.getIdentityId();

        globalDataFileList.stream()
                .filter(detail -> {//本组织数据加入localDataFileQueueFetchedFromStorage队列，有LocalDataRefreshTask更新Local_data_file的状态
                    if(localIdentityId.equals(detail.getIdentityId())){
                        try {
                            //将指定的元素插入此队列的尾部，如果该队列已满，则一直等到（阻塞）
                            localDataFileQueueFetchedFromStorage.offer(detail,60, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            log.error("本地组织数据放入队列失败",e);
                        }
                        return false;
                    } else {
                        return true;
                    }
                })
                .forEach(detail -> {//数据归类 TODO 性能方面考虑
                    if(existMetaDataIdList.contains(detail.getMetaDataId())){//如果已存在数据库，则进行更新
                          updateList.add(detail);
                    } else {//不存在，则表示是新增
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
        addList.stream().forEach(s->log.info(s.getMetaDataId()));
        batchAdd(addList);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    private void batchAdd(List<GlobalDataFile> addList) {
        List<GlobalDataFile> localDataFileList = new ArrayList<>();
        List<GlobalMetaDataColumn> columnList = new ArrayList<>();
        addList.forEach(detail -> {
            localDataFileList.add(detail);
            columnList.addAll(detail.getMetaDataColumnList());
        });

        //批量新增
        BatchExecuteUtil.batchExecute(1000,localDataFileList,(tempList)->{
            globalDataFileMapper.batchInsert(tempList);
        });
        BatchExecuteUtil.batchExecute(1000,columnList,(tempList)->{
            globalMetaDataColumnMapper.batchInsert(tempList);
        });
    }

    private void batchUpdate(List<GlobalDataFile> updateList) {
        //批量更新
        BatchExecuteUtil.batchExecute(1000,updateList,(tempList)->{
            globalDataFileMapper.batchUpdate(tempList);
        });
    }
}
