package com.platon.metis.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalSeedNode;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.seed.*;
import com.platon.metis.admin.service.LocalSeedNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author houzhuang
 * 种子节点控制类 */
@RestController
@RequestMapping("/api/v1/seednode/")
@Api(tags = "种子节点控制类")
@Slf4j
public class LocalSeedNodeController {

    @Resource
    LocalSeedNodeService localSeedNodeService;


    @PostMapping("/addSeedNode")
    @ApiOperation(value="新增种子节点", response = JsonResponse.class)
    public JsonResponse addSeedNode(@Validated @RequestBody AddSeedNodeReq addSeedNodeReq) {
        LocalSeedNode localSeedNode = new LocalSeedNode();
        localSeedNode.setSeedNodeId(addSeedNodeReq.getSeedNodeId());
        localSeedNodeService.insertSeedNode(localSeedNode);

        return JsonResponse.success();
    }

    @PostMapping("/deleteSeedNode")
    @ApiOperation(value="删除种子节点", response = JsonResponse.class)
    public JsonResponse deleteSeedNode(@Validated @RequestBody DeleteSeedNodeReq deleteSeedNodeReq) {
        localSeedNodeService.deleteSeedNode(deleteSeedNodeReq.getSeedNodeId());
        return JsonResponse.success();
    }

    @PostMapping("/listSeedNode")
    @ApiOperation(value="查询种子节点服务列表", response = JsonResponse.class)
    public JsonResponse<Page<LocalSeedNode>> listSeedNode(@Validated @RequestBody ListSeedNodeReq seedReq) {
        Page<LocalSeedNode> page = localSeedNodeService.listSeedNode(seedReq.getPageNumber(), seedReq.getPageSize());
        return JsonResponse.page(page);
    }


    @PostMapping("/seedNodeDetails")
    @ApiOperation(value="查询种子节点详情", response = JsonResponse.class)
    public JsonResponse<LocalSeedNode> querySeedNodeDetails(@Validated @RequestBody SeedQueryDetailsReq seedDetailsReq) {
        LocalSeedNode localSeedNode = localSeedNodeService.querySeedNodeDetails(seedDetailsReq.getSeedNodeId());
        return JsonResponse.success(localSeedNode);
    }

    @PostMapping("/checkSeedNodeId")
    @ApiOperation(value="校验种子节点是否可用", response = JsonResponse.class)
    public JsonResponse checkSeedNodeId(@Validated @RequestBody CheckSeedNodeIdReq checkSeedNodeIdReq) {
        localSeedNodeService.checkSeedNodeId(checkSeedNodeIdReq.getSeedNodeId());
        return JsonResponse.success();
    }
}
