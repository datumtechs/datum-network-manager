package com.platon.metis.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ArgumentException;
import com.platon.metis.admin.common.exception.NodeNameExists;
import com.platon.metis.admin.common.exception.NodeNameIllegal;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.dao.entity.LocalPowerLoadSnapshot;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.dao.entity.PowerLoad;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.*;
import com.platon.metis.admin.service.LocalPowerNodeService;
import com.platon.metis.admin.service.TaskService;
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
 * 计算节点控制类 */
@RestController
@RequestMapping("/api/v1/powernode")
@Api(tags = "计算节点控制类")
@Slf4j
public class LocalPowerNodeController {

    @Resource
    LocalPowerNodeService localPowerNodeService;

    @Resource
    TaskService taskService;

    /*@PostMapping("/addPowerNode")
    @ApiOperation(value="新增计算节点", response = JsonResponse.class)
    public JsonResponse addPowerNode(@Validated @RequestBody PowerAddReq powerAddReq) {
        LocalPowerNode localPowerNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerAddReq, localPowerNode);

        //localPowerNode.status =
        localPowerNodeService.insertPowerNode(localPowerNode);
        return JsonResponse.success();
    }*/

    @PostMapping("/updateNodeName")
    @ApiOperation(value="修改计算节点名称", response = JsonResponse.class)
    public JsonResponse updateNodeName(@Validated @RequestBody PowerUpdateReq powerUpdateReq) {
        if(powerUpdateReq == null || StringUtils.isBlank(powerUpdateReq.getNodeId()) || StringUtils.isBlank(powerUpdateReq.getNodeName())){
            throw new ArgumentException();
        }
        if(!NameUtil.isValidName(powerUpdateReq.getNodeName())){
            throw new NodeNameIllegal();
        }
        LocalPowerNode localPowerNode = localPowerNodeService.findLocalPowerNodeByName(powerUpdateReq.getNodeName());
        if (localPowerNode!=null){
            if (StringUtils.equals(localPowerNode.getNodeId(), powerUpdateReq.getNodeId())) {
                return JsonResponse.success();
            }else{
                throw new NodeNameExists();
            }
        }
        localPowerNodeService.updateLocalPowerNodeName(powerUpdateReq.getNodeId(), powerUpdateReq.getNodeName());
        return JsonResponse.success();
    }

    /*@PostMapping("/deletePowerNode")
    @ApiOperation(value="删除计算节点", response = JsonResponse.class)
    public JsonResponse deletePowerNode(@Validated @RequestBody PowerDeleteReq powerDeleteReq) {
        localPowerNodeService.deletePowerNodeByNodeId(powerDeleteReq.getPowerNodeId());
        return JsonResponse.success();
    }*/

    @PostMapping("/powerNodeDetails")
    @ApiOperation(value="查询计算节点详情", response = JsonResponse.class)
    public JsonResponse<LocalPowerNode> powerNodeDetails(@Validated @RequestBody PowerQueryDetailsReq queryDetailsReq) {
        LocalPowerNode localPowerNode = localPowerNodeService.findPowerNodeDetails(queryDetailsReq.getPowerNodeId());
        return JsonResponse.success(localPowerNode);
    }

    @PostMapping("/listPowerNode")
    @ApiOperation(value="查询计算节点列表", response = JsonResponse.class)
    public JsonResponse<List<LocalPowerNode>> listPowerNode(@Validated @RequestBody PowerQueryListReq powerReq) {
        Page<LocalPowerNode> page = localPowerNodeService.listPowerNode(powerReq.getIdentityId(), powerReq.getKeyword(), powerReq.getPageNumber(), powerReq.getPageSize());
        return JsonResponse.page(page);
    }

    @PostMapping("/publishPower")
    @ApiOperation(value="启用算力", response = JsonResponse.class)
    public JsonResponse switchPower(@Validated @RequestBody PowerPublishReq powerPublishReq) {
        localPowerNodeService.publishPower(powerPublishReq.getPowerNodeId());
        return JsonResponse.success();
    }

    @PostMapping("/revokePower")
    @ApiOperation(value="停用算力", response = JsonResponse.class)
    public JsonResponse revokePower(@Validated @RequestBody PowerRevokeReq powerRevokeReq) {
        localPowerNodeService.revokePower(powerRevokeReq.getPowerNodeId());
        return JsonResponse.success();
    }

/*    @PostMapping("/listPowerNodeUseHistory")
    @ApiOperation(value="查询计算节点历史记录", response = JsonResponse.class)
    public JsonResponse listPowerNodeUseHistory(@Validated @RequestBody PowerHistoryReq powerHistoryReq) {
        List dataList = localPowerNodeService.queryPowerNodeUseHistory(powerHistoryReq.getPowerNodeId(),
                powerHistoryReq.getResourceType(), powerHistoryReq.getTimeType());
        return JsonResponse.success(dataList);
    }*/

    @PostMapping("/listRunningTaskByPowerNodeId")
    @ApiOperation(value="查询计算节点参与的正在计算中的任务列表", response = JsonResponse.class)
    public JsonResponse<Page<Task>> listRunningTaskByPowerNodeId(@Validated @RequestBody PowerJoinTaskReq powerJoinTaskReq) {
        Page<Task> page = taskService.listRunningTaskByPowerNodeId(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());

        return JsonResponse.page(page);
    }

/*    @PostMapping("/checkPowerNodeName")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkPowerNodeName(@Validated @RequestBody PowerCheckNameReq powerCheckNameReq) {
        localPowerNodeService.checkPowerNodeName(powerCheckNameReq.getPowerNodeName());
        return JsonResponse.success();
    }*/

    @GetMapping("/listLocalPowerLoadSnapshotByPowerNodeId")
    @ApiOperation(value="查询算力节点的最近24小时的负载情况", response = JsonResponse.class)
    public JsonResponse<List<LocalPowerLoadSnapshot>> listLocalPowerLoadSnapshotByPowerNodeId(@ApiParam(name = "powerNodeId",value = "算力节点ID", type = "String", required = true) @RequestParam String powerNodeId) {
        //参数，最近多少小时的数据
        List<LocalPowerLoadSnapshot> list = localPowerNodeService.listLocalPowerLoadSnapshotByNodeId(powerNodeId, 24);
        return JsonResponse.success(list);
    }

    @GetMapping("/getCurrentLocalPowerLoadByPowerNodeId")
    @ApiOperation(value="查询算力节点当前的负载情况", response = JsonResponse.class)
    public JsonResponse<PowerLoad> getCurrentLocalPowerLoadByPowerNodeId(@ApiParam(name = "powerNodeId",value = "算力节点ID", type = "String", required = true) @RequestParam String powerNodeId) {
        PowerLoad list = localPowerNodeService.getCurrentLocalPowerLoadByNodeId(powerNodeId);
        return JsonResponse.success(list);
    }

}
