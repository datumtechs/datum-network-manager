package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.GlobalDataFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/24 11:54
 * @Version
 * @Desc
 */

/**
 * 刷新本组织数据状态
 */

@Slf4j
//@Component
public class LocalDataRefreshTask implements ApplicationRunner {

    @Resource
    private LocalMetaDataMapper localMetaDataMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("本组织数据文件更新线程启动...");
        while(true){
            try {
                //获取并移除此队列的头部，如果没有元素则等待（阻塞）
                GlobalDataFile globalDataFile = GlobalDataRefreshTask.localDataFileQueueFetchedFromStorage.take();
                globalDataFile.getStatus();
                localMetaDataMapper.updateStatusByFileId( globalDataFile.getFileId(),  globalDataFile.getStatus());
            } catch (Exception e) {
                log.error("本组织数据文件更新线程运行出错",e);
            }
        }
    }

}
