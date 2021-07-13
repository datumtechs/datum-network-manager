package com.platon.rosettanet.admin.controller;


import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.*;
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
     * @param powerAddReq
     * @return
     */
    @PostMapping("/addPowerNode")
    public JsonResponse addPowerNode(PowerAddReq powerAddReq) {
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerAddReq, localComputeNode);
        // 新增计算节点
        localPowerNodeService.insertPowerNode(localComputeNode);
        return JsonResponse.success("新增成功");
    }

    /**
     * 修改计算节点
     * @param powerUpdateReq
     * @return
     */
    @PostMapping("/updatePowerNode")
    public JsonResponse updatePowerNode(PowerUpdateReq powerUpdateReq) {
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerUpdateReq, localComputeNode);
        // 修改计算节点
        localPowerNodeService.updatePowerNodeByNodeId(localComputeNode);
        return JsonResponse.success();
    }

    /**
     * 删除计算节点
     * @param powerDeleteReq
     * @return
     */
    @PostMapping("/deletePowerNode")
    public JsonResponse deletePowerNode(PowerDeleteReq powerDeleteReq) {
        // 删除计算节点
        int count = localPowerNodeService.deletePowerNodeByNodeId(powerDeleteReq.getPowerNodeId());
        if (count == 0) {
            JsonResponse.fail("删除失败！");
        }
        return JsonResponse.success();
    }

    /**
     * 查询计算节点服务列表
     * @param powerReq
     * @return
     */
    @PostMapping("/queryPowerNodeList")
    public JsonResponse queryPowerNodeList(PowerQueryListReq powerReq) {
        PageInfo PageInfo = localPowerNodeService.queryPowerNodeList(powerReq.getIdentityId(),
                powerReq.getKeyword(), powerReq.getPageNumber(), powerReq.getPageSize());
        return JsonResponse.success(PageInfo);
    }

    /**
     * 启用/停用算力
     * @param powerSwitchReq
     * @return
     */
    @PostMapping("/switchPower")
    public JsonResponse switchPower(PowerSwitchReq powerSwitchReq) {
        localPowerNodeService.switchPower(powerSwitchReq.getPowerNodeId(), powerSwitchReq.getStatus());
        return JsonResponse.success();
    }

    /**
     * 查询计算节点使用资源
     * @param powerNodeId
     * @return
     */
    @PostMapping("/queryPowerNodeUseResource")
    public JsonResponse queryPowerNodeUseResource(String powerNodeId) {
        localPowerNodeService.queryPowerNodeUseResource(powerNodeId);
        return JsonResponse.success();
    }

    /**
     * 查询计算节点参数任务列表
     * @param powerJoinTaskReq
     * @return
     */
    @PostMapping("/queryPowerJoinTaskList")
    public JsonResponse queryPowerJoinTaskList(PowerJoinTaskReq powerJoinTaskReq) {
        PageInfo PageInfo = localPowerNodeService.queryPowerJoinTaskList(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());
        return JsonResponse.success(PageInfo);
    }

}
