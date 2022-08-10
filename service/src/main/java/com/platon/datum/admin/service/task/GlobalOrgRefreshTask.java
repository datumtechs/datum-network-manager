package com.platon.datum.admin.service.task;

import cn.hutool.core.util.StrUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.GlobalOrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.entity.GlobalOrg;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.service.DataSyncService;
import com.platon.datum.admin.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * 定时刷新本组织的调度服务连接状态，调度服务状态和入网状态
 */

@Slf4j
@Configuration
public class GlobalOrgRefreshTask {

    @Resource
    private GlobalOrgMapper globalOrgMapper;
    @Resource
    private AuthClient authClient;
    @Resource
    private DataSyncService dataSyncService;
    @Resource
    private OrgService orgService;
    @Resource
    private ApplyRecordMapper applyRecordMapper;


    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${GlobalOrgRefreshTask.fixedDelay}")
    public void task() {
        if (OrgCache.identityIdNotFound()) {
            return;
        }
        log.debug("刷新全网组织定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.GlobalOrg);
            List<GlobalOrg> identityList = authClient.getIdentityList(dataSync.getLatestSynced());
            if (CollectionUtils.isEmpty(identityList)) {
                break;
            }
            //判断是否包含本组织的更新
            String localOrgIdentityId = OrgCache.getLocalOrgIdentityId();
            identityList.forEach(globalOrg -> {
                if (globalOrg.getIdentityId().equals(localOrgIdentityId)) {
                    String credential = globalOrg.getCredential();
                    orgService.updateCredential(credential);
                    if (StrUtil.isBlank(credential)) {
                        applyRecordMapper.removeUsed();
                    }
                }
            });

            //更新元数据
            globalOrgMapper.insertOrUpdate(identityList);
            //把最近更新时间update到数据库
            LocalDateTime updateTime = identityList.stream()
                    .sorted(Comparator.comparing(GlobalOrg::getRecUpdateTime).reversed())
                    .findFirst()
                    .get()
                    .getRecUpdateTime();

            dataSync.setLatestSynced(updateTime);
            dataSyncService.updateDataSync(dataSync);

            if (identityList.size() < GrpcConstant.PageSize) {
                break;
            }
        }
        log.debug("刷新全网组织定时任务结束|||");
    }

}
