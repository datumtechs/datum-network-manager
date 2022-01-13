package com.platon.metis.admin.controller.overview;

import com.platon.metis.admin.dao.dto.DataAuthReqDTO;
import com.platon.metis.admin.dao.dto.StatsPowerTrendDTO;
import com.platon.metis.admin.dao.dto.UsedResourceDTO;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.resp.index.MyTaskStatsResp;
import com.platon.metis.admin.dto.resp.index.UsedResourceResp;
import com.platon.metis.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/v1/overview")
@Slf4j
public class OverviewController {

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

    @ApiOperation(value = "查询本地计算资源占用情况")
    @GetMapping("/localPowerUsage")
    public JsonResponse<UsedResourceResp> localPowerUsage(){
        UsedResourceDTO usedResourceDTO = indexService.queryUsedTotalResource();
        return JsonResponse.success(usedResourceDTO);
    }

    @ApiOperation(value = "查询我发布的数据")
    @GetMapping("/localDataFileStatsTrendMonthly")
    public JsonResponse<List<StatsPowerTrendDTO>> localDataFileStatsTrendMonthly(){
        List<StatsPowerTrendDTO> dataPowerList = indexService.listLocalDataFileStatsTrendMonthly();
        return JsonResponse.success(dataPowerList);
    }

    @ApiOperation(value = "查询我发布的算力")
    @GetMapping("/localPowerStatsTrendMonthly")
    public JsonResponse<List<StatsPowerTrendDTO>> localPowerStatsTrendMonthly(){
        List<StatsPowerTrendDTO> dataPowerList = indexService.listLocalPowerStatsTrendMonthly();
        return JsonResponse.success(dataPowerList);
    }

    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/myTaskOverview")
    public JsonResponse<MyTaskStatsResp> queryMyCalculateTaskStats(){
        List<Map<String, Object>> list = indexService.queryMyCalculateTaskStats();
        List<MyTaskStatsResp> respList = new ArrayList<>();
        for(Map<String, Object> map : list){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                MyTaskStatsResp resp = new MyTaskStatsResp();
                resp.setStatus( entry.getKey());
                resp.setStatusCount((Integer)entry.getValue());
                respList.add(resp);
            }
        }
        return JsonResponse.success(respList);
    }

    /*@ApiOperation(value = "查询全网算力总量走势")
    @GetMapping("/globalPowerStatsTrendMonthly")
    public JsonResponse<List<StatsTrendDTO>> listGlobalPowerStatsTrendMonthly(){
        List<StatsTrendDTO> list = indexService.listGlobalPowerStatsTrendMonthly();
        return JsonResponse.success(list);
    }

    @ApiOperation(value = "查询全网有效数据总量，每月新发布数据量走势")
    @GetMapping("/globalDataFileStatsTrendMonthly")
    public JsonResponse<List<StatsTrendDTO>> listGlobalDataFileStatsTrendMonthly(){
        List<StatsTrendDTO> list = indexService.listGlobalDataFileStatsTrendMonthly();
        return JsonResponse.success(list);

    }

    @ApiOperation(value = "查询全网数据总量环比")
    @GetMapping("/queryWholeNetDateRatio")
    @Deprecated
    public JsonResponse<DataRatioResp> queryWholeNetDateRatio(){
        Map<String, Object> map = indexService.queryWholeNetDateTotalRatio();

        DataRatioResp resp = new DataRatioResp();
        resp.setRingRatio((String)map.get("ringRatio"));
        resp.setRingRatio((String)map.get("sameRatio"));
        return JsonResponse.success(resp);
    }*/

    @ApiOperation(value = "查询数据待授权列表")
    @GetMapping("/listDataAuthReqWaitingForApprove")
    public JsonResponse<DataAuthReqDTO> listDataAuthReqWaitingForApprove(){
        List<DataAuthReqDTO> list = indexService.listDataAuthReqWaitingForApprove();
        return JsonResponse.success(list == null || list.size() == 0 ? new ArrayList<>() : list);
    }

}
