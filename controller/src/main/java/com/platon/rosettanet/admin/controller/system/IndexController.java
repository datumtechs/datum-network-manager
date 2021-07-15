package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;
import com.platon.rosettanet.admin.dto.resp.IndexOverviewResp;
import com.platon.rosettanet.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

/**
 * 系统首页相关接口
 */

@Api(tags = "系统概况")
@RestController
@RequestMapping("/api/v1/system/index/")
public class IndexController {

    @Resource
    private IndexService indexService;


    /**
     * 查看当前系统的概览信息,即首页信息，该接口内容为统计信息
     * @return
     */
    @ApiOperation(value = "首页信息统计信息")
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
    @ApiOperation(value = "首页计算节点列表")
    @GetMapping("nodeList")
    public JsonResponse<List<IndexNodeListResp>> nodeList(){
        List<LocalPowerNode> powerNodeList = indexService.getPowerNodeList();
        List<IndexNodeListResp> respList= powerNodeList.stream()
                .map(IndexNodeListResp::from)
                .collect(Collectors.toList());
        return JsonResponse.success(respList);
    }
}
