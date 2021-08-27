package com.platon.rosettanet.admin.controller.system;

import cn.hutool.core.bean.BeanUtil;
import com.platon.rosettanet.admin.dao.dto.UsedResourceDTO;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.index.DataAndPowerReq;
import com.platon.rosettanet.admin.dto.req.index.WholeNetDataReq;
import com.platon.rosettanet.admin.dto.resp.index.*;
import com.platon.rosettanet.admin.service.IndexService;
import com.platon.rosettanet.admin.util.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

/**
 * @author h
 * 系统首页相关接口
 */

@Api(tags = "系统概况")
@RestController
@RequestMapping("/api/v1/system/index")
@Slf4j
public class IndexController {

    @Resource
    private IndexService indexService;


//    /**
//     * 查看当前系统的概览信息,即首页信息，该接口内容为统计信息
//     * @return
//     */
//    @ApiOperation(value = "首页信息统计信息")
//    @GetMapping("/overview")
//    public JsonResponse<IndexOverviewResp> overview(){
//        VLocalStats localStats = indexService.getOverview();
//        IndexOverviewResp resp = IndexOverviewResp.from(localStats);
//        return JsonResponse.success(resp);
//    }
//
//
//    /**
//     * 即首页信息计算节点列表
//     * @return
//     */
//    @ApiOperation(value = "首页计算节点列表")
//    @GetMapping("/nodeList")
//    public JsonResponse<List<IndexNodeListResp>> nodeList(){
//        List<LocalPowerNode> powerNodeList = indexService.getPowerNodeList();
//        List<IndexNodeListResp> respList= powerNodeList.stream()
//                .map(IndexNodeListResp::from)
//                .collect(Collectors.toList());
//        return JsonResponse.success(respList);
//    }

    @ApiOperation(value = "查询总计算资源占用情况")
    @GetMapping("/queryUsedTotalResource")
    public JsonResponse<UsedResourceResp> queryUsedTotalResource(){
        try {
            UsedResourceDTO usedResourceDTO = indexService.queryUsedTotalResource();
            UsedResourceResp usedResourceResp = BeanUtil.toBean(usedResourceDTO, UsedResourceResp.class);
            return JsonResponse.success(usedResourceResp);
        } catch (Exception e) {
            log.error("index--queryUsedTotalResource--查询总计算资源占用情况, 错误信息:{}", e);
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询我发布的数据或算力")
    @PostMapping("/queryPublishDataOrPower")
    public JsonResponse queryPublishDataAndPower(@RequestBody @Validated DataAndPowerReq dataAndPowerReq){
        try {
            List<Long> dataPowerList = indexService.queryPublishDataOrPower(dataAndPowerReq.getFlag());
            return JsonResponse.success(dataPowerList);
        } catch (Exception e) {
            log.error("index--queryPublishDataOrPower--查询我发布的数据或算力, 错误信息:{}", e);
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/queryMyCalculateTaskStats")
    public JsonResponse<MyTaskStatsResp> queryMyCalculateTaskStats(){
        try {
            List list = indexService.queryMyCalculateTaskStats();
            List<MyTaskStatsResp> myTaskStatsRespList = ConvertUtil.convertParallelToList(list, MyTaskStatsResp.class);
            return JsonResponse.success(myTaskStatsRespList);
        } catch (Exception e) {
            log.error("index--queryMyCalculateTaskStats--查询我的计算任务概况, 错误信息:{}", e);
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询全网数据或算力总量走势")
    @PostMapping("/queryWholeNetDateOrPower")
    public JsonResponse queryWholeNetDateAndPower(@Validated @RequestBody WholeNetDataReq wholeNetDataReq){
        try {
            List<Long> list = indexService.queryWholeNetDateOrPower(wholeNetDataReq.getFlag());
            return JsonResponse.success(list);
        } catch (Exception e) {
            log.error("index--queryWholeNetDateOrPower--查询全网数据或算力总量走势, 错误信息:{}", e);
            return JsonResponse.fail(e.getMessage());
        }

    }

    @ApiOperation(value = "查询全网数据总量环比")
    @GetMapping("/queryWholeNetDateRatio")
    public JsonResponse<DataRatioResp> queryWholeNetDateRatio(){
        Map<String, Object> map = indexService.queryWholeNetDateTotalRatio();
        DataRatioResp dataRatioResp = BeanUtil.toBean(map, DataRatioResp.class);
        return JsonResponse.success(dataRatioResp);
    }

    @ApiOperation(value = "查询数据待授权列表")
    @PostMapping("/queryDataWaitAuthList")
    public JsonResponse<DataRatioResp> queryDataWaitAuthList(){
        Map<String, Object> map = indexService.queryDataWaitAuthList();
        DataRatioResp dataRatioResp = BeanUtil.toBean(map, DataRatioResp.class);
        return JsonResponse.success(dataRatioResp);
    }

}
