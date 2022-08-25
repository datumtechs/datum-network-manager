package com.platon.datum.admin.controller.overview;

import com.platon.datum.admin.controller.BaseController;
import com.platon.datum.admin.dao.dto.*;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.resp.MyTaskStatsResp;
import com.platon.datum.admin.dto.resp.UsedResourceResp;
import com.platon.datum.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统首页相关接口
 *
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

@Api(tags = "系统概况")
@RestController
@RequestMapping("/api/v1/overview/")
@Slf4j
public class OverviewController extends BaseController {

    @Resource
    private IndexService indexService;


    @ApiOperation(value = "查询本组织计算资源占用情况")
    @GetMapping("/localPowerUsage")
    public JsonResponse<UsedResourceResp> localPowerUsage() {
        UsedResourceDTO usedResourceDTO = indexService.queryUsedTotalResource();
        return JsonResponse.success(usedResourceDTO);
    }

    @ApiOperation(value = "查询我发布的数据")
    @GetMapping("/localDataFileStatsTrendMonthly")
    public JsonResponse<List<StatsDataTrendDTO>> localDataFileStatsTrendMonthly(HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        List<StatsDataTrendDTO> dataPowerList = indexService.listLocalDataFileStatsTrendMonthly(currentUserAddress);
        return JsonResponse.success(dataPowerList);
    }

    @ApiOperation(value = "查询本组织发布的算力")
    @GetMapping("/localPowerStatsTrendMonthly")
    public JsonResponse<List<StatsPowerTrendDTO>> localPowerStatsTrendMonthly() {
        List<StatsPowerTrendDTO> dataPowerList = indexService.listLocalPowerStatsTrendMonthly();
        return JsonResponse.success(dataPowerList);
    }

    @ApiOperation(value = "查询我的计算任务概况")
    @GetMapping("/myTaskOverview")
    public JsonResponse<MyTaskStatsResp> queryMyCalculateTaskStats(HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        List<Map<String, Object>> list = indexService.queryMyCalculateTaskStats(currentUserAddress, currentUserIsAdmin(session));
        List<MyTaskStatsResp> respList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            MyTaskStatsResp resp = new MyTaskStatsResp();
            resp.setStatus(Integer.parseInt(map.get("status").toString()));
            resp.setStatusCount(map.get("statusCount") == null ? 0 : Integer.parseInt(map.get("statusCount").toString()));
            respList.add(resp);
        }
        return JsonResponse.success(respList);
    }

    @ApiOperation(value = "查询我的数据待授权列表")
    @GetMapping("/listDataAuthReqWaitingForApprove")
    public JsonResponse<DataAuthReqDTO> listDataAuthReqWaitingForApprove(HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        List<DataAuthReqDTO> list = indexService.listDataAuthReqWaitingForApprove(currentUserAddress);
        return JsonResponse.success(list == null || list.size() == 0 ? new ArrayList<>() : list);
    }

    @ApiOperation(value = "查询我的数据凭证概况")
    @GetMapping("/dataTokenOverview")
    public JsonResponse<DataTokenOverviewDTO> dataTokenOverview(HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        DataTokenOverviewDTO dataTokenOverviewDTO = indexService.listDataTokenOverview(currentUserAddress);
        return JsonResponse.success(dataTokenOverviewDTO);
    }

}
