package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dao.dto.UsedResourceDTO;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.index.DataAndPowerReq;
import com.platon.rosettanet.admin.dto.req.index.WholeNetDataReq;
import com.platon.rosettanet.admin.dto.resp.index.*;
import com.platon.rosettanet.admin.service.IndexService;
import com.platon.rosettanet.admin.util.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
        UsedResourceDTO usedResourceDTO = indexService.queryUsedTotalResource();
        UsedResourceResp usedResourceResp = (UsedResourceResp)ConvertUtil.ConvertToResp(
                usedResourceDTO, new UsedResourceResp());
        return JsonResponse.success(usedResourceResp);
    }

    @ApiOperation(value = "查询我发布的数据或算力")
    @PostMapping("/queryPublishDataOrPower")
    public JsonResponse<MyPublishDataResp> queryPublishDataAndPower(@RequestBody @Validated DataAndPowerReq dataAndPowerReq){
        MyPublishDataResp myPublishDataResp = new MyPublishDataResp();
        List<Long> dataPowerList = indexService.queryPublishDataOrPower(dataAndPowerReq.getFlag());
        return JsonResponse.success(dataPowerList);
    }

    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/queryMyPowerTaskStats")
    public JsonResponse<MyTaskStatsResp> queryMyPowerTaskStats(){
        MyTaskStatsResp myTaskStatsResp = new MyTaskStatsResp();
        Map<String, Object> map = indexService.queryMyPowerTaskStats();
        BeanUtils.copyProperties(map, myTaskStatsResp);
        return JsonResponse.success(myTaskStatsResp);
    }

    @ApiOperation(value = "查询全网数据或算力总量走势", response = WholeNetDataResp.class)
    @PostMapping("/queryWholeNetDateAndPower")
    public JsonResponse<WholeNetDataResp> queryWholeNetDateAndPower(@Validated @RequestBody WholeNetDataReq wholeNetDataReq){
        WholeNetDataResp wholeNetDataResp = new WholeNetDataResp();
        Map<String, Object> map = indexService.queryWholeNetDateAndPower(wholeNetDataReq.getFlag());
        BeanUtils.copyProperties(map, wholeNetDataResp);
        return JsonResponse.success(wholeNetDataResp);
    }

    @ApiOperation(value = "查询全网数据总量环比", response = WholeNetDataResp.class)
    @GetMapping("/queryWholeNetDateRatio")
    public JsonResponse<WholeNetDataResp> queryWholeNetDateRatio(){
        DataRatioResp dataRatioResp = new DataRatioResp();
        Map<String, Object> map = indexService.queryWholeNetDateTotalRatio();
        BeanUtils.copyProperties(map, dataRatioResp);
        return JsonResponse.success(dataRatioResp);
    }

}
