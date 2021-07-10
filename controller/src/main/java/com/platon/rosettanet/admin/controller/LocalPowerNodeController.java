package com.platon.rosettanet.admin.controller;


import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.PowerAddReq;
import com.platon.rosettanet.admin.dto.req.PowerDeleteReq;
import com.platon.rosettanet.admin.dto.req.PowerListSelectReq;
import com.platon.rosettanet.admin.dto.req.PowerUpdateReq;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerAddReq, localComputeNode);
        // 新增计算节点
        localPowerNodeService.insertPowerNode(localComputeNode);
        return JsonResponse.success("新增成功");
    }

    /**
     * 修改计算节点
     * @param powerUpdateReq
     * @return
     */
    @PostMapping("/updatePowerNode")
    public JsonResponse updatePowerNode(PowerUpdateReq powerUpdateReq) {
        LocalPowerNode localComputeNode = new LocalPowerNode();
        BeanUtils.copyProperties(powerUpdateReq, localComputeNode);
        // 修改计算节点
        localPowerNodeService.updatePowerNodeByNodeId(localComputeNode);
        return JsonResponse.success();
    }

    /**
     * 删除计算节点
     * @param powerAddReq
     * @return
     */
    @PostMapping("/deletePowerNode")
    public JsonResponse deletePowerNode(PowerDeleteReq powerAddReq) {
        // 删除计算节点
        int count = localPowerNodeService.deletePowerNodeByNodeId(powerAddReq.getPowerNodeId());
        if (count == 0) {
            JsonResponse.fail("删除失败");
        }
        return JsonResponse.success();
    }

    /**
     * 查询计算节点服务列表
     * @param powerListSelectReq
     * @return
     */
    @PostMapping("/listNode")
    public JsonResponse selectPowerListByNodeId(PowerListSelectReq powerListSelectReq) {
        // 查询计算节点详情
        localPowerNodeService.selectPowerDetailByNodeId(powerListSelectReq.getIdentityId());
        return JsonResponse.success();
    }

//    /**
//     * 根据节点id查询节点详情
//     * @param selectPowerReq
//     * @return
//     */
//    @PostMapping("/detailPowerNode")
//    public JsonResponse selectPowerDetailByNodeId(PowerSelectReq selectPowerReq) {
//        // 查询计算节点详情
//        localPowerNodeService.selectPowerDetailByNodeId(selectPowerReq.getPowerNodeId());
//        return JsonResponse.success();
//    }

}
