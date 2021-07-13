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
import java.util.List;

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
        LocalPowerNode localPowerNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerAddReq, localPowerNode);
        // 新增计算节点
        localPowerNodeService.insertPowerNode(localPowerNode);
        return JsonResponse.success("新增成功");
    }

    /**
     * 修改计算节点
     * @param powerUpdateReq
     * @return
     */
    @PostMapping("/updatePowerNode")
    public JsonResponse updatePowerNode(PowerUpdateReq powerUpdateReq) {
        LocalPowerNode localPowerNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerUpdateReq, localPowerNode);
        // 修改计算节点
        localPowerNodeService.updatePowerNodeByNodeId(localPowerNode);
        return JsonResponse.success("修改成功");
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
     * 查询计算节点详情
     * @param powerNodeId
     * @return
     */
    @PostMapping("/queryPowerNodeDetails")
    public JsonResponse queryPowerNodeDetails(String powerNodeId) {
        LocalPowerNode localPowerNode = localPowerNodeService.queryPowerNodeDetails(powerNodeId);
        return JsonResponse.success(localPowerNode);
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
     * 启用算力
     * @param powerSwitchReq
     * @return
     */
    @PostMapping("/switchPower")
    public JsonResponse switchPower(PowerSwitchReq powerSwitchReq) {
        localPowerNodeService.publishPower(powerSwitchReq.getPowerNodeId());
        return JsonResponse.success("启用成功");
    }

    /**
     * 停用算力
     * @param powerSwitchReq
     * @return
     */
    @PostMapping("/revokePower")
    public JsonResponse revokePower(PowerSwitchReq powerSwitchReq) {
        localPowerNodeService.revokePower(powerSwitchReq.getPowerNodeId());
        return JsonResponse.success("停用成功");
    }

    /**
     * 查询计算节点使用资源
     * @param powerNodeId
     * @return
     */
    @PostMapping("/queryPowerNodeUseResource")
    public JsonResponse queryPowerNodeUseResource(String powerNodeId) {
        List dataList = localPowerNodeService.queryPowerNodeUseResource(powerNodeId);
        return JsonResponse.success(dataList);
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
