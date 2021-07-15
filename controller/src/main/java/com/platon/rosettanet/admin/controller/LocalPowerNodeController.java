package com.platon.rosettanet.admin.controller;


import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.*;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "计算节点控制类")
public class LocalPowerNodeController {

    @Resource
    LocalPowerNodeService localPowerNodeService;


    @PostMapping("/addPowerNode")
    @ApiOperation(value="新增计算节点", response = JsonResponse.class)
    public JsonResponse addPowerNode(@Validated @RequestBody PowerAddReq powerAddReq) {
        LocalPowerNode localPowerNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerAddReq, localPowerNode);
        // 新增计算节点
        localPowerNodeService.insertPowerNode(localPowerNode);
        return JsonResponse.success("新增成功");
    }

    @PostMapping("/updatePowerNode")
    @ApiOperation(value="修改计算节点", response = JsonResponse.class)
    public JsonResponse updatePowerNode(@Validated @RequestBody PowerUpdateReq powerUpdateReq) {
        LocalPowerNode localPowerNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerUpdateReq, localPowerNode);
        // 修改计算节点
        localPowerNodeService.updatePowerNodeByNodeId(localPowerNode);
        return JsonResponse.success("修改成功");
    }

    @PostMapping("/deletePowerNode")
    @ApiOperation(value="删除计算节点", response = JsonResponse.class)
    public JsonResponse deletePowerNode(@Validated @RequestBody PowerDeleteReq powerDeleteReq) {
        // 删除计算节点
        int count = localPowerNodeService.deletePowerNodeByNodeId(powerDeleteReq.getPowerNodeId());
        if (count == 0) {
            JsonResponse.fail("删除失败！");
        }
        return JsonResponse.success();
    }

    @PostMapping("/queryPowerNodeDetails")
    @ApiOperation(value="查询计算节点详情", response = JsonResponse.class)
    public JsonResponse queryPowerNodeDetails(@Validated @RequestBody PowerQueryDetailsReq queryDetailsReq) {
        LocalPowerNode localPowerNode = localPowerNodeService.queryPowerNodeDetails(queryDetailsReq.getPowerNodeId());
        return JsonResponse.success(localPowerNode);
    }

    @PostMapping("/queryPowerNodeList")
    @ApiOperation(value="查询计算节点服务列表", response = JsonResponse.class)
    public JsonResponse queryPowerNodeList(@Validated @RequestBody PowerQueryListReq powerReq) {
        PageInfo PageInfo = localPowerNodeService.queryPowerNodeList(powerReq.getIdentityId(),
                powerReq.getKeyword(), powerReq.getPageNumber(), powerReq.getPageSize());
        return JsonResponse.success(PageInfo);
    }

    @PostMapping("/publishPower")
    @ApiOperation(value="启用算力", response = JsonResponse.class)
    public JsonResponse switchPower(@Validated @RequestBody PowerSwitchReq powerSwitchReq) {
        localPowerNodeService.publishPower(powerSwitchReq.getPowerNodeId());
        return JsonResponse.success("启用成功");
    }

    @PostMapping("/revokePower")
    @ApiOperation(value="停用算力", response = JsonResponse.class)
    public JsonResponse revokePower(@Validated @RequestBody PowerSwitchReq powerSwitchReq) {
        localPowerNodeService.revokePower(powerSwitchReq.getPowerNodeId());
        return JsonResponse.success("停用成功");
    }

    @PostMapping("/queryPowerNodeUseResource")
    @ApiOperation(value="查询计算节点使用资源", response = JsonResponse.class)
    public JsonResponse queryPowerNodeUseResource(@Validated @RequestBody PowerHistoryResourcesReq historyResourcesReq) {
        List dataList = localPowerNodeService.queryPowerNodeUseResource(historyResourcesReq.getPowerNodeId());
        return JsonResponse.success(dataList);
    }

    @PostMapping("/queryPowerJoinTaskList")
    @ApiOperation(value="查询计算节点参数任务列表", response = JsonResponse.class)
    public JsonResponse queryPowerJoinTaskList(@Validated @RequestBody PowerJoinTaskReq powerJoinTaskReq) {
        PageInfo PageInfo = localPowerNodeService.queryPowerJoinTaskList(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());
        return JsonResponse.success(PageInfo);
    }

}
