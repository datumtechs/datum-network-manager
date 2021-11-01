package com.platon.metis.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.*;
import com.platon.metis.admin.service.LocalPowerNodeService;
import com.platon.metis.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/powernode")
@Api(tags = "计算节点控制类")
@Slf4j
public class LocalPowerNodeController {

    @Resource
    LocalPowerNodeService localPowerNodeService;

    @Resource
    TaskService taskService;

    @PostMapping("/addPowerNode")
    @ApiOperation(value="新增计算节点", response = JsonResponse.class)
    public JsonResponse addPowerNode(@Validated @RequestBody PowerAddReq powerAddReq) {
        long startTime = System.currentTimeMillis();
        try {
            LocalPowerNode localPowerNode = new LocalPowerNode();
            BeanUtils.copyProperties(powerAddReq, localPowerNode);

            //localPowerNode.status =
            localPowerNodeService.insertPowerNode(localPowerNode);
            return JsonResponse.success("新增成功");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("addPowerNode接口, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "新增失败！");
        }
    }

    @PostMapping("/updatePowerNode")
    @ApiOperation(value="修改计算节点", response = JsonResponse.class)
    public JsonResponse updatePowerNode(@Validated @RequestBody PowerUpdateReq powerUpdateReq) {
        long startTime = System.currentTimeMillis();
        try {
            LocalPowerNode localPowerNode = new LocalPowerNode();
            BeanUtils.copyProperties(powerUpdateReq, localPowerNode);
            localPowerNodeService.updatePowerNodeByNodeId(localPowerNode);
            return JsonResponse.success("修改成功！");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("updatePowerNode接口, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "修改失败！");
        }
    }

    @PostMapping("/deletePowerNode")
    @ApiOperation(value="删除计算节点", response = JsonResponse.class)
    public JsonResponse deletePowerNode(@Validated @RequestBody PowerDeleteReq powerDeleteReq) {
        long startTime = System.currentTimeMillis();
        try {
            localPowerNodeService.deletePowerNodeByNodeId(powerDeleteReq.getPowerNodeId());
            return JsonResponse.success("删除成功！");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("deletePowerNode接口, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "删除失败！");
        }
    }

    @PostMapping("/queryPowerNodeDetails")
    @ApiOperation(value="查询计算节点详情", response = JsonResponse.class)
    public JsonResponse<LocalPowerNode> queryPowerNodeDetails(@Validated @RequestBody PowerQueryDetailsReq queryDetailsReq) {
        LocalPowerNode localPowerNode = localPowerNodeService.queryPowerNodeDetails(queryDetailsReq.getPowerNodeId());
        return JsonResponse.success(localPowerNode);
    }

    @PostMapping("/listPowerNode")
    @ApiOperation(value="查询计算节点列表", response = JsonResponse.class)
    public JsonResponse<List<LocalPowerNode>> queryPowerNodeList(@Validated @RequestBody PowerQueryListReq powerReq) {
        try {
            Page<LocalPowerNode> page = localPowerNodeService.queryPowerNodeList(powerReq.getIdentityId(), powerReq.getKeyword(), powerReq.getPageNumber(), powerReq.getPageSize());
            return JsonResponse.page(page);
        } catch (Exception e) {
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "查询失败！");
        }
    }

    @PostMapping("/publishPower")
    @ApiOperation(value="启用算力", response = JsonResponse.class)
    public JsonResponse switchPower(@Validated @RequestBody PowerPublishReq powerPublishReq) {
        localPowerNodeService.publishPower(powerPublishReq.getPowerNodeId());
        return JsonResponse.success("启用成功");
    }

    @PostMapping("/revokePower")
    @ApiOperation(value="停用算力", response = JsonResponse.class)
    public JsonResponse revokePower(@Validated @RequestBody PowerRevokeReq powerRevokeReq) {
        localPowerNodeService.revokePower(powerRevokeReq.getPowerNodeId());
        return JsonResponse.success("停用成功");
    }

    @PostMapping("/listPowerNodeUseHistory")
    @ApiOperation(value="查询计算节点历史记录", response = JsonResponse.class)
    public JsonResponse listPowerNodeUseHistory(@Validated @RequestBody PowerHistoryReq powerHistoryReq) {
        List dataList = localPowerNodeService.queryPowerNodeUseHistory(powerHistoryReq.getPowerNodeId(),
                powerHistoryReq.getResourceType(), powerHistoryReq.getTimeType());
        return JsonResponse.success(dataList);
    }

    @PostMapping("/listRunningTaskByPowerNodeId")
    @ApiOperation(value="查询计算节点参与的正在计算中的任务列表", response = JsonResponse.class)
    public JsonResponse<List<LocalPowerJoinTask>> listRunningTaskByPowerNodeId(@Validated @RequestBody PowerJoinTaskReq powerJoinTaskReq) {
        Page<LocalPowerJoinTask> page = localPowerNodeService.queryPowerJoinTaskList(powerJoinTaskReq.getPowerNodeId(),
                powerJoinTaskReq.getPageNumber(), powerJoinTaskReq.getPageSize());

        return JsonResponse.page(page);
    }

    @PostMapping("/checkPowerNodeName")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkPowerNodeName(@Validated @RequestBody PowerCheckNameReq powerCheckNameReq) {
        long startTime = System.currentTimeMillis();
        try {
            localPowerNodeService.checkPowerNodeName(powerCheckNameReq.getPowerNodeName());
            return JsonResponse.success("校验成功");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("checkPowerNodeName接口执行失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "校验失败！");
        }
    }

}