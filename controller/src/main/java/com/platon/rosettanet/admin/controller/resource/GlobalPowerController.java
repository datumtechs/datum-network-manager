package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import com.platon.rosettanet.admin.dto.CommonPageReq;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.GlobalPowerListByKeyWordReq;
import com.platon.rosettanet.admin.dto.resp.GlobalPowerPageResp;
import com.platon.rosettanet.admin.service.GlobalPowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/12 2:50
 * @Version
 * @Desc
 */

@Api(tags = "算力中心")
@RestController
@RequestMapping("/api/v1/resource/powercenter/")
public class GlobalPowerController {


    @Resource
    private GlobalPowerService globalPowerService;

    /**
     * 展示数据列表，带分页
     */
    @ApiOperation(value = "数据分页列表")
    @PostMapping("powerList")
    public JsonResponse<List<GlobalPowerPageResp>> page(@RequestBody @Validated CommonPageReq req){
        Page<GlobalPower> globalPowerPage = globalPowerService.listGlobalPower(req.getPageNumber(), req.getPageSize(),null);
        List<GlobalPowerPageResp> respList = globalPowerPage.getResult().stream()
                .map(GlobalPowerPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalPowerPage,respList);
    }

    /**
     * 根据关键字查询全网算力信息
     */
    @ApiOperation(value = "关键字查询")
    @PostMapping("powerListByKeyWord")
    public JsonResponse<List<GlobalPowerPageResp>> listByKeyWord(@RequestBody @Validated GlobalPowerListByKeyWordReq req){
        Page<GlobalPower> globalPowerPage = globalPowerService.listGlobalPower(req.getPageNumber(), req.getPageSize(),req.getKeyword());
        List<GlobalPowerPageResp> respList = globalPowerPage.getResult().stream()
                .map(GlobalPowerPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalPowerPage,respList);
    }
}
