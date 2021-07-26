package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.common.util.BatchExecuteUtil;
import com.platon.rosettanet.admin.dao.GlobalDataFileMapper;
import com.platon.rosettanet.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.GlobalMetaDataColumn;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.dao.enums.LocalOrgStatusEnum;
import com.platon.rosettanet.admin.grpc.client.MetaDataClient;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.rosettanet.admin.service.exception.ServiceException;
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

import static com.platon.rosettanet.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

/**
 * @Author liushuyu
 * @Date 2021/7/12 11:48
 * @Version
 * @Desc 组织状态刷新,以及缓存刷新
 */

@Slf4j
@Component
public class LocalOrgRefreshTask {

    @Resource
    private LocalOrgMapper localOrgMapper;
    @Resource
    private YarnClient yarnClient;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void task(){
        LocalOrg localOrg = localOrgMapper.select();
        if(localOrg == null){
            log.info("请先申请身份标识");
            //刷新缓存
            LocalOrgCache.setLocalOrgInfo(null);
            LocalOrgIdentityCache.setIdentityId(null);
            return;
        }
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
            return;
        } else {
            localOrg.setCarrierStatus(nodeInfo.getState());
            localOrg.setCarrierNodeId(nodeInfo.getNodeId());
        }
        if(localOrg.getIdentityId().equals(nodeInfo.getIdentityId())){//相同表示入网了
            localOrg.setStatus(LocalOrgStatusEnum.JOIN.getStatus());
        } else {
            localOrg.setStatus(LocalOrgStatusEnum.LEAVE.getStatus());
        }
        localOrgMapper.updateSelective(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
    }

}
