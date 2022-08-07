package com.platon.datum.admin.service.task;

import com.platon.datum.admin.common.exception.MetadataAuthorized;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.DataAuthMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataAuth;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.enums.DataAuthStatusEnum;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


/**
 * 数据授权申请定时任务
 * 定期从调度服务获取和本组织有关的数据授权申请。
 * <p>
 * 数据授权，涉及FLow(提出、撤回授权申请），调度服务，Admin（审批授权申请），Flow在撤回授权申请，Admin在同意授权申请时，都要以调度服务返回的结果为准。
 * 1. 管理台只能对处于pending状态的申请进行审批；
 * 2. 对已经同意的授权申请，也可以提前终止授权
 *
 * @deprecated 0.4.0版本已废弃掉授权，取而代之的是数据凭证
 */

@Deprecated
@Slf4j
//@Configuration
public class DataAuthReqRefreshTask {

    @Resource
    private AuthClient authClient;
    @Resource
    private DataAuthMapper dataAuthMapper;
    @Resource
    private DataSyncService dataSyncService;

    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${DataAuthReqRefreshTask.fixedDelay}")
    public void task() {
        if(OrgCache.identityIdNotFound()){
            return;
        }
        log.debug("刷新数据授权申请(查询过期申请)定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.DataAuthReq);

            List<DataAuth> dataAuthList = authClient.getMetaDataAuthorityList(dataSync.getLatestSynced());

            //可以单独设置每个grpc请求的超时
            //List<DataAuth> dataAuthList = authClient.getMetaDataAuthorityList(dataSync.getLatestSynced(), fixedDelay);
            if (CollectionUtils.isEmpty(dataAuthList)) {
                break;
            }

            log.debug("本次更新数据授权申请数量：{}", dataAuthList.size());

            dataAuthMapper.replaceBatch(dataAuthList);

            DataAuth dataAuth = dataAuthList.stream()
                    .sorted(Comparator.comparing(DataAuth::getRecUpdateTime).reversed())
                    .findFirst()
                    .get();
            dataSync.setLatestSynced(dataAuth.getRecUpdateTime());
            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);
            if (dataAuthList.size() < GrpcConstant.PageSize) {
                break;
            }
        }
        dataAuthMapper.updateExpireAuthData();
        //元数据撤销之后修改授权信息为拒绝
        List<String> authIdList = dataAuthMapper.selectAuthIdListWithRevokedMetaData();
        authIdList.forEach(authId -> {
            try {
                this.refuseAuth(authId);
            } catch (Exception e) {
                log.warn(e.getMessage() + "--发起拒绝失败，authId:{}", authId);
            }
        });


        log.debug("刷新数据授权申请(查询过期申请)定时任务结束|||");
    }

    private void refuseAuth(String authId) {
        DataAuth localDataAuth = dataAuthMapper.selectByPrimaryKey(authId);
        if (Objects.isNull(localDataAuth)) {
            throw new ArithmeticException();
        }
        if (localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()) {
            log.warn("data auth request is processed already.");
            throw new MetadataAuthorized();
        }
        authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.REFUSE.getStatus(), "meta data was revoked, just refuse it.");

        DataAuth dataAuth = new DataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(DataAuthStatusEnum.REFUSE.getStatus());
        dataAuth.setAuthAt(LocalDateTimeUtil.now());
        dataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }
}
