package com.platon.rosettanet.admin.controller;


import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.InsertPowerReq;
import com.platon.rosettanet.admin.dto.req.SelectPowerReq;
import com.platon.rosettanet.admin.dto.req.UpdatePowerReq;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author houzhuang
 * 计算节点控制类
 */
@RestController
@RequestMapping("/api/v1/node/powernode")
public class LocalPowerNodeController {

    @Resource
    LocalPowerNodeService localPowerNodeService;

    /**
     * 新增计算节点
     * @param insertComputeReq
     * @return
     */
    @PostMapping("/addPowerNode")
    public JsonResponse addPowerNode(InsertPowerReq insertComputeReq) {
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(insertComputeReq, localComputeNode);
        // 新增计算节点
        localPowerNodeService.insertPowerNode(localComputeNode);
        return JsonResponse.success();
    }

    /**
     * 修改计算节点
     * @param updateComputeReq
     * @return
     */
    @PostMapping("/updatePowerNode")
    public JsonResponse updatePowerNode(UpdatePowerReq updateComputeReq) {
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(updateComputeReq, localComputeNode);
        // 修改计算节点
        localPowerNodeService.updatePowerNodeByNodeId(localComputeNode);
        return JsonResponse.success();
    }

    /**
     * 删除计算节点
     * @param selectPowerReq
     * @return
     */
    @PostMapping("/deletePowerNode")
    public JsonResponse deletePowerNode(SelectPowerReq selectPowerReq) {
        // 删除计算节点
        localPowerNodeService.deletePowerNodeByNodeId(selectPowerReq.getNodeId());
        return JsonResponse.success();
    }

    /**
     * 根据节点id查询节点详情
     * @param selectPowerReq
     * @return
     */
    @PostMapping("/detailPowerNode")
    public JsonResponse selectPowerDetailByNodeId(SelectPowerReq selectPowerReq) {
        // 查询计算节点详情
        localPowerNodeService.selectPowerDetailByNodeId(selectPowerReq.getNodeId());
        return JsonResponse.success();
    }

}
