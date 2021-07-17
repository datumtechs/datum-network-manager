package com.platon.rosettanet.admin.controller.node;


import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.common.util.NameUtil;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.*;
import com.platon.rosettanet.admin.dto.resp.LocalPowerNodeResp;
import com.platon.rosettanet.admin.dto.resp.PowerJoinTaskResp;
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
 * 计算节点控制类 */
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
            return JsonResponse.fail("删除失败！");
        }
        return JsonResponse.success("删除成功！");
    }

    @PostMapping("/queryPowerNodeDetails")
    @ApiOperation(value="查询计算节点详情", response = JsonResponse.class)
    public JsonResponse<LocalPowerNodeResp> queryPowerNodeDetails(@Validated @RequestBody PowerQueryDetailsReq queryDetailsReq) {
        LocalPowerNode localPowerNode = localPowerNodeService.queryPowerNodeDetails(queryDetailsReq.getPowerNodeId());
        return JsonResponse.success(localPowerNode);
    }

    @PostMapping("/queryPowerNodeList")
    @ApiOperation(value="查询计算节点服务列表", response = JsonResponse.class)
    public JsonResponse<LocalPowerNodeResp> queryPowerNodeList(@Validated @RequestBody PowerQueryListReq powerReq) {
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

    @PostMapping("/queryPowerNodeUseHistory")
    @ApiOperation(value="查询计算节点历史记录", response = JsonResponse.class)
    public JsonResponse queryPowerNodeUseHistory(@Validated @RequestBody PowerHistoryReq powerHistoryReq) {
        List dataList = localPowerNodeService.queryPowerNodeUseHistory(powerHistoryReq.getPowerNodeId());
        return JsonResponse.success(dataList);
    }

    @PostMapping("/queryPowerJoinTaskList")
    @ApiOperation(value="查询计算节点参与任务列表", response = JsonResponse.class)
    public JsonResponse<PowerJoinTaskResp> queryPowerJoinTaskList(@Validated @RequestBody PowerJoinTaskReq powerJoinTaskReq) {
        PageInfo PageInfo = localPowerNodeService.queryPowerJoinTaskList(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());
        return JsonResponse.success(PageInfo);
    }

    @PostMapping("/checkPowerNodeName")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkPowerNodeName(@Validated @RequestBody PowerCheckNameReq powerCheckNameReq) {
        if (NameUtil.isValidName(powerCheckNameReq.getPowerNodeName())) {
            return JsonResponse.fail("名称不符合命名规则！");
        }
        int count = localPowerNodeService.checkPowerNodeName(powerCheckNameReq.getPowerNodeName());
        if (count > 0) {
            return JsonResponse.fail("校验失败");
        }
        return JsonResponse.success("校验成功");
    }

}
