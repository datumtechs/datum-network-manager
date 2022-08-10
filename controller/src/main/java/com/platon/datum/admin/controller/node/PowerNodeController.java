package com.platon.datum.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.ArgumentException;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.NameUtil;
import com.platon.datum.admin.dao.entity.PowerLoad;
import com.platon.datum.admin.dao.entity.PowerLoadSnapshot;
import com.platon.datum.admin.dao.entity.PowerNode;
import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.service.PowerNodeService;
import com.platon.datum.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houzhuang
 * 计算节点控制类
 */
@RestController
@RequestMapping("/api/v1/powernode/")
@Api(tags = "计算节点控制类")
@Slf4j
public class PowerNodeController {

    @Resource
    PowerNodeService powerNodeService;

    @Resource
    TaskService taskService;

    /*@PostMapping("/addPowerNode")
    @ApiOperation(value="新增计算节点", response = JsonResponse.class)
    public JsonResponse addPowerNode(@Validated @RequestBody PowerAddReq powerAddReq) {
        PowerNode localPowerNode = new PowerNode();
        BeanUtils.copyProperties(powerAddReq, localPowerNode);

        //localPowerNode.status =
        powerNodeService.insertPowerNode(localPowerNode);
        return JsonResponse.success();
    }*/

    @PostMapping("/updateNodeName")
    @ApiOperation(value = "修改计算节点名称", response = JsonResponse.class)
    public JsonResponse updateNodeName(@Validated @RequestBody PowerUpdateReq powerUpdateReq) {
        if (powerUpdateReq == null || StringUtils.isBlank(powerUpdateReq.getNodeId()) || StringUtils.isBlank(powerUpdateReq.getNodeName())) {
            throw new ArgumentException();
        }
        if (!NameUtil.isValidName(powerUpdateReq.getNodeName())) {
            throw new BizException(Errors.NodeNameIllegal);
        }
        PowerNode powerNode = powerNodeService.findLocalPowerNodeByName(powerUpdateReq.getNodeName());
        if (powerNode != null) {
            if (StringUtils.equals(powerNode.getNodeId(), powerUpdateReq.getNodeId())) {
                return JsonResponse.success();
            } else {
                throw new BizException(Errors.NodeNameExists);
            }
        }
        powerNodeService.updateLocalPowerNodeName(powerUpdateReq.getNodeId(), powerUpdateReq.getNodeName());
        return JsonResponse.success();
    }

    /*@PostMapping("/deletePowerNode")
    @ApiOperation(value="删除计算节点", response = JsonResponse.class)
    public JsonResponse deletePowerNode(@Validated @RequestBody PowerDeleteReq powerDeleteReq) {
        powerNodeService.deletePowerNodeByNodeId(powerDeleteReq.getPowerNodeId());
        return JsonResponse.success();
    }*/

    @PostMapping("/powerNodeDetails")
    @ApiOperation(value = "查询计算节点详情", response = JsonResponse.class)
    public JsonResponse<PowerNode> powerNodeDetails(@Validated @RequestBody PowerQueryDetailsReq queryDetailsReq) {
        PowerNode powerNode = powerNodeService.findPowerNodeDetails(queryDetailsReq.getPowerNodeId());
        return JsonResponse.success(powerNode);
    }

    @PostMapping("/listPowerNode")
    @ApiOperation(value = "查询计算节点列表", response = JsonResponse.class)
    public JsonResponse<List<PowerNode>> listPowerNode(@Validated @RequestBody PowerQueryListReq powerReq) {
        Page<PowerNode> page = powerNodeService.listPowerNode(powerReq.getIdentityId(), powerReq.getKeyword(), powerReq.getPageNumber(), powerReq.getPageSize());
        return JsonResponse.page(page);
    }

    @PostMapping("/publishPower")
    @ApiOperation(value = "启用算力", response = JsonResponse.class)
    public JsonResponse switchPower(@Validated @RequestBody PowerPublishReq powerPublishReq) {
        powerNodeService.publishPower(powerPublishReq.getPowerNodeId());
        return JsonResponse.success();
    }

    @PostMapping("/revokePower")
    @ApiOperation(value = "停用算力", response = JsonResponse.class)
    public JsonResponse revokePower(@Validated @RequestBody PowerRevokeReq powerRevokeReq) {
        powerNodeService.revokePower(powerRevokeReq.getPowerNodeId());
        return JsonResponse.success();
    }

/*    @PostMapping("/listPowerNodeUseHistory")
    @ApiOperation(value="查询计算节点历史记录", response = JsonResponse.class)
    public JsonResponse listPowerNodeUseHistory(@Validated @RequestBody PowerHistoryReq powerHistoryReq) {
        List dataList = powerNodeService.queryPowerNodeUseHistory(powerHistoryReq.getPowerNodeId(),
                powerHistoryReq.getResourceType(), powerHistoryReq.getTimeType());
        return JsonResponse.success(dataList);
    }*/

    @PostMapping("/listRunningTaskByPowerNodeId")
    @ApiOperation(value = "查询计算节点参与的正在计算中的任务列表", response = JsonResponse.class)
    public JsonResponse<Page<Task>> listRunningTaskByPowerNodeId(@Validated @RequestBody PowerJoinTaskReq powerJoinTaskReq) {
        Page<Task> page = taskService.listRunningTaskByPowerNodeId(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());

        return JsonResponse.page(page);
    }

/*    @PostMapping("/checkPowerNodeName")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkPowerNodeName(@Validated @RequestBody PowerCheckNameReq powerCheckNameReq) {
        powerNodeService.checkPowerNodeName(powerCheckNameReq.getPowerNodeName());
        return JsonResponse.success();
    }*/

    @GetMapping("/listLocalPowerLoadSnapshotByPowerNodeId")
    @ApiOperation(value = "查询算力节点的最近24小时的负载情况", response = JsonResponse.class)
    public JsonResponse<List<PowerLoadSnapshot>> listLocalPowerLoadSnapshotByPowerNodeId(@ApiParam(name = "powerNodeId", value = "算力节点ID", type = "String", required = true) @RequestParam String powerNodeId) {
        //参数，最近多少小时的数据
        List<PowerLoadSnapshot> list = powerNodeService.listLocalPowerLoadSnapshotByNodeId(powerNodeId, 24);
        return JsonResponse.success(list);
    }

    @GetMapping("/getCurrentLocalPowerLoadByPowerNodeId")
    @ApiOperation(value = "查询算力节点当前的负载情况", response = JsonResponse.class)
    public JsonResponse<PowerLoad> getCurrentLocalPowerLoadByPowerNodeId(@ApiParam(name = "powerNodeId", value = "算力节点ID", type = "String", required = true) @RequestParam String powerNodeId) {
        PowerLoad list = powerNodeService.getCurrentLocalPowerLoadByNodeId(powerNodeId);
        return JsonResponse.success(list);
    }

}
