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
@RequestMapping("/api/v1/seednode")
@Api(tags = "种子节点控制类")
@Slf4j
public class LocalSeedNodeController {

    @Resource
    LocalSeedNodeService localSeedNodeService;


    @PostMapping("/addSeedNode")
    @ApiOperation(value="新增种子节点", response = JsonResponse.class)
    public JsonResponse addSeedNode(@Validated @RequestBody AddSeedNodeReq addSeedNodeReq) {
        long startTime = System.currentTimeMillis();
        try {
            LocalSeedNode localSeedNode = new LocalSeedNode();
            localSeedNode.setSeedNodeId(addSeedNodeReq.getSeedNodeId());
            localSeedNodeService.insertSeedNode(localSeedNode);

            return JsonResponse.success("新增成功");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("addSeedNode--接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "新增失败！");
        }
    }

    @PostMapping("/deleteSeedNode")
    @ApiOperation(value="删除种子节点", response = JsonResponse.class)
    public JsonResponse deleteSeedNode(@Validated @RequestBody DeleteSeedNodeReq deleteSeedNodeReq) {

        try {
            localSeedNodeService.deleteSeedNode(deleteSeedNodeReq.getSeedNodeId());
            return JsonResponse.success("删除成功！");
        } catch (Exception e) {
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "删除失败！");
        }
    }

    @PostMapping("/listSeedNode")
    @ApiOperation(value="查询种子节点服务列表", response = JsonResponse.class)
    public JsonResponse<Page<LocalSeedNode>> listSeedNode(@Validated @RequestBody ListSeedNodeReq seedReq) {
        try {
            Page<LocalSeedNode> page = localSeedNodeService.listSeedNode(seedReq.getPageNumber(), seedReq.getPageSize());
            return JsonResponse.page(page);
        } catch (Exception e) {
            log.error("查询种子节点服务列表错误", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "查询失败！");
        }
    }


    @PostMapping("/seedNodeDetails")
    @ApiOperation(value="查询种子节点详情", response = JsonResponse.class)
    public JsonResponse<LocalSeedNode> querySeedNodeDetails(@Validated @RequestBody SeedQueryDetailsReq seedDetailsReq) {
        LocalSeedNode localSeedNode = localSeedNodeService.querySeedNodeDetails(seedDetailsReq.getSeedNodeId());
        return JsonResponse.success(localSeedNode);
    }

    @PostMapping("/checkSeedNodeId")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkSeedNodeId(@Validated @RequestBody CheckSeedNodeIdReq checkSeedNodeIdReq) {
        long startTime = System.currentTimeMillis();
        try {
            localSeedNodeService.checkSeedNodeId(checkSeedNodeIdReq.getSeedNodeId());
            return JsonResponse.success("校验成功");
        } catch (Exception e) {
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "校验失败！");
        }
    }
}
