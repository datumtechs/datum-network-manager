package com.platon.rosettanet.admin.controller.system;

import cn.hutool.core.bean.BeanUtil;
import com.platon.rosettanet.admin.dao.dto.UsedResourceDTO;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.index.DataAndPowerReq;
import com.platon.rosettanet.admin.dto.req.index.WholeNetDataReq;
import com.platon.rosettanet.admin.dto.resp.index.*;
import com.platon.rosettanet.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统首页相关接口
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
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
            return JsonResponse.success(BeanUtil.toBean(usedResourceDTO, UsedResourceResp.class));
        } catch (Exception e) {
            log.error("index--queryUsedTotalResource--查询总计算资源占用情况, 错误信息:{}", e.getMessage());
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询我发布的数据或算力")
    @PostMapping("/queryPublishDataOrPower")
    public JsonResponse<List<Long>> queryPublishDataAndPower(@RequestBody @Validated DataAndPowerReq dataAndPowerReq){
        try {
            List<Long> dataPowerList = indexService.queryPublishDataOrPower(dataAndPowerReq.getFlag());
            return JsonResponse.success(dataPowerList);
        } catch (Exception e) {
            log.error("index--queryPublishDataOrPower--查询我发布的数据或算力, 错误信息:{}", e.getMessage());
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/queryMyCalculateTaskStats")
    public JsonResponse<MyTaskStatsResp> queryMyCalculateTaskStats(){
        try {
            List<Map<String, Object>> list = indexService.queryMyCalculateTaskStats();
            return JsonResponse.success(BeanUtil.copyToList(list, MyTaskStatsResp.class));
        } catch (Exception e) {
            log.error("index--queryMyCalculateTaskStats--查询我的计算任务概况, 错误信息:{}", e.getMessage());
            return JsonResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询全网数据或算力总量走势")
    @PostMapping("/queryWholeNetDateOrPower")
    public JsonResponse<List<Long>> queryWholeNetDateAndPower(@Validated @RequestBody WholeNetDataReq wholeNetDataReq){
        try {
            List<Long> list = indexService.queryWholeNetDateOrPower(wholeNetDataReq.getFlag());
            return JsonResponse.success(list);
        } catch (Exception e) {
            log.error("index--queryWholeNetDateOrPower--查询全网数据或算力总量走势, 错误信息:{}", e.getMessage());
            return JsonResponse.fail(e.getMessage());
        }

    }

    @ApiOperation(value = "查询全网数据总量环比")
    @GetMapping("/queryWholeNetDateRatio")
    public JsonResponse<DataRatioResp> queryWholeNetDateRatio(){
        Map<String, Object> map = indexService.queryWholeNetDateTotalRatio();
        return JsonResponse.success(BeanUtil.toBean(map, DataRatioResp.class));
    }

    @ApiOperation(value = "查询数据待授权列表")
    @GetMapping("/queryWaitAuthDataList")
    public JsonResponse<WaitAuthDataResp> queryWaitAuthDataList(){
        List<Map<String, Object>> list = indexService.queryWaitAuthDataList();
        return JsonResponse.success(list == null || list.size() == 0 ? new ArrayList<>() :
                BeanUtil.copyToList(list, WaitAuthDataResp.class));
    }

}
