package com.platon.metis.admin.controller.node;


import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalSeedNode;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.seed.*;
import com.platon.metis.admin.dto.resp.seed.LocalSeedNodeListResp;
import com.platon.metis.admin.dto.resp.seed.SeedNodeDetailsResp;
import com.platon.metis.admin.service.LocalSeedNodeService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author houzhuang
 * 种子节点控制类 */
@RestController
@RequestMapping("/api/v1/node/seednode")
@Api(tags = "种子节点控制类")
@Slf4j
public class LocalSeedNodeController {

    @Resource
    LocalSeedNodeService localSeedNodeService;


    @PostMapping("/addSeedNode")
    @ApiOperation(value="新增种子节点", response = JsonResponse.class)
    public JsonResponse addSeedNode(@Validated @RequestBody SeedAddReq seedAddReq) {
        long startTime = System.currentTimeMillis();
        try {
            LocalSeedNode localSeedNode = new LocalSeedNode();
            BeanUtils.copyProperties(seedAddReq, localSeedNode);
            localSeedNodeService.insertSeedNode(localSeedNode);
            return JsonResponse.success("新增成功");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("addSeedNode--接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "新增失败！");
        }
    }

    @PostMapping("/updateSeedNode")
    @ApiOperation(value="修改种子节点", response = JsonResponse.class)
    public JsonResponse updateSeedNode(@Validated @RequestBody SeedUpdateReq seedUpdateReq) {
        long startTime = System.currentTimeMillis();
        try {
            LocalSeedNode localSeedNode = new LocalSeedNode();
            BeanUtils.copyProperties(seedUpdateReq, localSeedNode);
            localSeedNodeService.updateSeedNode(localSeedNode);
            return JsonResponse.success("修改成功！");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("updateSeedNode--接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "修改失败！");
        }
    }

    @PostMapping("/deleteSeedNode")
    @ApiOperation(value="删除种子节点", response = JsonResponse.class)
    public JsonResponse deleteSeedNode(@Validated @RequestBody SeedDeleteReq seedDeleteReq) {
        long startTime = System.currentTimeMillis();
        try {
            localSeedNodeService.deleteSeedNode(seedDeleteReq.getSeedNodeId());
            return JsonResponse.success("删除成功！");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("deleteSeedNode--接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "删除失败！");
        }
    }

    @PostMapping("/querySeedNodeDetails")
    @ApiOperation(value="查询种子节点详情", response = JsonResponse.class)
    public JsonResponse<SeedNodeDetailsResp> querySeedNodeDetails(@Validated @RequestBody SeedQueryDetailsReq seedDetailsReq) {
        LocalSeedNode localSeedNode = localSeedNodeService.querySeedNodeDetails(seedDetailsReq.getSeedNodeId());
        return JsonResponse.success(localSeedNode);
    }

    @PostMapping("/querySeedNodeList")
    @ApiOperation(value="查询种子节点服务列表", response = JsonResponse.class)
    public JsonResponse<LocalSeedNodeListResp> querySeedNodeList(@Validated @RequestBody SeedQueryListReq seedReq) {
        long startTime = System.currentTimeMillis();
        try {
            Page<LocalSeedNode> page = localSeedNodeService.querySeedNodeList(seedReq.getKeyWord(),
                    seedReq.getPageNumber(), seedReq.getPageSize());
            // 处理返回数据
            List<LocalSeedNodeListResp> localSeedNodeRespList = new ArrayList<>();
            if (null != page && !page.getResult().isEmpty()) {
                page.getResult().stream().forEach(seedNode -> {
                    LocalSeedNodeListResp seedNodeListResp = new LocalSeedNodeListResp();
                    BeanUtils.copyProperties(seedNode, seedNodeListResp);
                    localSeedNodeRespList.add(seedNodeListResp);
                });
            }
            long diffTime = System.currentTimeMillis() - startTime;
            log.info("querySeedNodeList--接口成功, 执行时间:{}, 返回参数:{}", diffTime +"ms", page.toString());
            return JsonResponse.page(page, localSeedNodeRespList);
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("querySeedNodeList--接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "查询失败！");
        }
    }

    @PostMapping("/checkSeedNodeName")
    @ApiOperation(value="校验计算节点名称是否可用", response = JsonResponse.class)
    public JsonResponse checkSeedNodeName(@Validated @RequestBody SeedCheckNameReq seedCheckNameReq) {
        long startTime = System.currentTimeMillis();
        try {
            localSeedNodeService.checkSeedNodeName(seedCheckNameReq.getSeedNodeName());
            return JsonResponse.success("校验成功");
        } catch (Exception e) {
            long diffTime = System.currentTimeMillis() - startTime;
            log.error("checkSeedNodeName接口失败, 执行时间:{}, 错误信息:{}", diffTime +"ms", e);
            return JsonResponse.fail(e.getMessage() != null ? e.getMessage() : "校验失败！");
        }
    }

}
