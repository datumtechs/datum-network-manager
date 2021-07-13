package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface LocalPowerDetailsMapper {

    /**
     * 插入计算节点资源详情
     * @param localPowerDetails
     * @return
     */
    int insertPowerDetails(LocalPowerDetails localPowerDetails);

    /**
     * 查询计算节点资源详情
     * @param powerNodeId
     * @return
     */
    List<LocalPowerDetails> queryPowerDetails(String powerNodeId);

}