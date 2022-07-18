package com.platon.datum.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.SeedNode;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.service.SeedNodeService;
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
public class SeedNodeController {

    @Resource
    SeedNodeService seedNodeService;


    @PostMapping("/addSeedNode")
    @ApiOperation(value="新增种子节点", response = JsonResponse.class)
    public JsonResponse addSeedNode(@Validated @RequestBody AddSeedNodeReq addSeedNodeReq) {
        SeedNode seedNode = new SeedNode();
        seedNode.setSeedNodeId(addSeedNodeReq.getSeedNodeId());
        seedNodeService.insertSeedNode(seedNode);

        return JsonResponse.success();
    }

    @PostMapping("/deleteSeedNode")
    @ApiOperation(value="删除种子节点", response = JsonResponse.class)
    public JsonResponse deleteSeedNode(@Validated @RequestBody DeleteSeedNodeReq deleteSeedNodeReq) {
        seedNodeService.deleteSeedNode(deleteSeedNodeReq.getSeedNodeId());
        return JsonResponse.success();
    }

    @PostMapping("/listSeedNode")
    @ApiOperation(value="查询种子节点服务列表", response = JsonResponse.class)
    public JsonResponse<Page<SeedNode>> listSeedNode(@Validated @RequestBody ListSeedNodeReq seedReq) {
        Page<SeedNode> page = seedNodeService.listSeedNode(seedReq.getPageNumber(), seedReq.getPageSize());
        return JsonResponse.page(page);
    }


    @PostMapping("/seedNodeDetails")
    @ApiOperation(value="查询种子节点详情", response = JsonResponse.class)
    public JsonResponse<SeedNode> querySeedNodeDetails(@Validated @RequestBody SeedQueryDetailsReq seedDetailsReq) {
        SeedNode seedNode = seedNodeService.querySeedNodeDetails(seedDetailsReq.getSeedNodeId());
        return JsonResponse.success(seedNode);
    }

    @PostMapping("/checkSeedNodeId")
    @ApiOperation(value="校验种子节点是否可用", response = JsonResponse.class)
    public JsonResponse checkSeedNodeId(@Validated @RequestBody CheckSeedNodeIdReq checkSeedNodeIdReq) {
        seedNodeService.checkSeedNodeId(checkSeedNodeIdReq.getSeedNodeId());
        return JsonResponse.success();
    }
}
