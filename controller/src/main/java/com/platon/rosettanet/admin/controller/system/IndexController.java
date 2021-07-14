package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;
import com.platon.rosettanet.admin.dto.resp.IndexOverviewResp;
import com.platon.rosettanet.admin.service.IndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

/**
 * 系统首页相关接口
 */

@RestController
@RequestMapping("/api/v1/system/index/")
public class IndexController {

    @Resource
    private IndexService indexService;


    /**
     * 查看当前系统的概览信息,即首页信息，该接口内容为统计信息
     * @return
     */
    @GetMapping("overview")
    public JsonResponse<IndexOverviewResp> overview(){
        VLocalStats localStats = indexService.getOverview();
        IndexOverviewResp resp = IndexOverviewResp.from(localStats);
        return JsonResponse.success(resp);
    }


    /**
     * 即首页信息计算节点列表
     * @return
     */
    @GetMapping("nodeList")
    public JsonResponse<IndexNodeListResp> nodeList(){
        indexService.getPowerNodeList();
        return JsonResponse.success();
    }
}
