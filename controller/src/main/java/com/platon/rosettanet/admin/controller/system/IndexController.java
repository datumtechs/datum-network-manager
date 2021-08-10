package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.index.WholeNetDataReq;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;
import com.platon.rosettanet.admin.dto.resp.IndexOverviewResp;
import com.platon.rosettanet.admin.dto.resp.index.MyPublishDataResp;
import com.platon.rosettanet.admin.dto.resp.index.MyTaskStatsResp;
import com.platon.rosettanet.admin.dto.resp.index.UsedResourceResp;
import com.platon.rosettanet.admin.dto.resp.index.WholeNetDataResp;
import com.platon.rosettanet.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/v1/system/index")
public class IndexController {

    @Resource
    private IndexService indexService;


    /**
     * 查看当前系统的概览信息,即首页信息，该接口内容为统计信息
     * @return
     */
    @ApiOperation(value = "首页信息统计信息")
    @GetMapping("/overview")
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
    @GetMapping("/nodeList")
    public JsonResponse<List<IndexNodeListResp>> nodeList(){
        List<LocalPowerNode> powerNodeList = indexService.getPowerNodeList();
        List<IndexNodeListResp> respList= powerNodeList.stream()
                .map(IndexNodeListResp::from)
                .collect(Collectors.toList());
        return JsonResponse.success(respList);
    }

    /**
     * 查询总计算资源占用情况
     * @return
     */
    @ApiOperation(value = "查询总计算资源占用情况", response = UsedResourceResp.class)
    @GetMapping("/queryUsedTotalResource")
    public JsonResponse<UsedResourceResp> queryUsedTotalResource(){
        UsedResourceResp usedResourceResp = new UsedResourceResp();
        Map map = indexService.queryUsedTotalResource();
        BeanUtils.copyProperties(map, usedResourceResp);
        return JsonResponse.success(usedResourceResp);
    }
    /**
     * 查询我发布的数据
     * @return
     */
    @ApiOperation(value = "查询我发布的数据")
    @GetMapping("/queryMyPublishData")
    public JsonResponse<MyPublishDataResp> queryMyPublishData(){
        MyPublishDataResp myPublishDataResp = new MyPublishDataResp();
        Map map = indexService.queryMyPublishData();
        BeanUtils.copyProperties(map, myPublishDataResp);
        return JsonResponse.success(myPublishDataResp);
    }

    /**
     * 查询我的计算任务概况
     * @return
     */
    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/queryMyPowerTaskStats")
    public JsonResponse<MyTaskStatsResp> queryMyPowerTaskStats(){
        MyTaskStatsResp myTaskStatsResp = new MyTaskStatsResp();
        Map map = indexService.queryMyPowerTaskStats();
        BeanUtils.copyProperties(map, myTaskStatsResp);
        return JsonResponse.success(myTaskStatsResp);
    }

    /**
     * 查询全网数据或算力总量走势
     * @return
     */
    @ApiOperation(value = "查询全网数据或算力总量走势", response = WholeNetDataResp.class)
    @PostMapping("/queryWholeNetDateAndPower")
    public JsonResponse<WholeNetDataResp> queryWholeNetDateAndPower(@Validated @RequestBody WholeNetDataReq wholeNetDataReq){
        WholeNetDataResp wholeNetDataResp = new WholeNetDataResp();
        Map map = indexService.queryWholeNetDateAndPower(wholeNetDataReq.getFlag());
        BeanUtils.copyProperties(map, wholeNetDataResp);
        return JsonResponse.success(wholeNetDataResp);
    }

    /**
     * 查询全网数据总量
     * @return
     */
    @ApiOperation(value = "查询全网数据总量", response = WholeNetDataResp.class)
    @GetMapping("/queryWholeNetDateTotal")
    public JsonResponse<WholeNetDataResp> queryWholeNetDateTotalRatio(){
        WholeNetDataResp wholeNetDataResp = new WholeNetDataResp();
        Map map = indexService.queryWholeNetDateTotalRatio();
        BeanUtils.copyProperties(map, wholeNetDataResp);
        return JsonResponse.success(wholeNetDataResp);
    }

}
