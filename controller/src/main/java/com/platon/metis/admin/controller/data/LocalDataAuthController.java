package com.platon.metis.admin.controller.data;

import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ArgumentException;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.dao.entity.LocalDataAuthDetail;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.auth.AuthDataActionReq;
import com.platon.metis.admin.dto.req.auth.AuthPageReq;
import com.platon.metis.admin.dto.resp.auth.LocalDataAuthDetailResp;
import com.platon.metis.admin.dto.resp.auth.LocalDataAuthPageResp;
import com.platon.metis.admin.dto.resp.auth.LocalDataAuthStatisticsResp;
import com.platon.metis.admin.enums.DtoAuthStatusEnum;
import com.platon.metis.admin.service.LocalDataAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc 数据授权
 */


@Api(tags = "数据授权")
@Slf4j
@RestController
@RequestMapping("/api/v1/data/")
public class LocalDataAuthController {

    @Resource
    private LocalDataAuthService localDataAuthService;

    /**
     * 授权数据列表，带分页
     */
    @ApiOperation(value = "授权数据列表分页查询")
    @PostMapping("/listLocalDataAuth")
    public JsonResponse<List<LocalDataAuthPageResp>> listLocalDataAuth(@RequestBody @Validated AuthPageReq req){
        Integer status = req.getStatus();
        if(status == null){
            status = 0;
        }
        if(status != DtoAuthStatusEnum.AUTH_UNDEFINED.getStatus() &&
                status != DtoAuthStatusEnum.AUTH_UNFINISH.getStatus() &&
                status != DtoAuthStatusEnum.AUTH_FINISH.getStatus()){
            throw new ArgumentException();
        }
        //数据库中，授权数据状态：0：等待授权审核，1:同意， 2:拒绝
        //所以，如果输入参数status=0，则查询（0：等待授权审核，1:同意， 2:拒绝）
        //如果输入参数status=1，则查询（0：等待授权审核）
        //如果输入参数status=2，则查询（1:同意， 2:拒绝）
        Page<LocalDataAuth> localDataAuthPage = localDataAuthService.listLocalDataAuth(req.getPageNumber(), req.getPageSize(), status, req.getKeyWord());
        List<LocalDataAuthPageResp> localDataAuthPageList = localDataAuthPage.getResult().stream().map(LocalDataAuthPageResp::from).collect(Collectors.toList());
        return JsonResponse.page(localDataAuthPage, localDataAuthPageList);
    }



    /**
     * 授权数据数量统计
     */
    @ApiOperation(value = "授权数据数量统计")
    @GetMapping("/dataAuthStatistics")
    public JsonResponse<LocalDataAuthStatisticsResp> dataAuthStatistics(){

        int finishAuthCount = localDataAuthService.selectFinishAuthCount();
        int unFinishAuthCount = localDataAuthService.selectUnfinishAuthCount();
        LocalDataAuthStatisticsResp statisticsDataAuthResp = new LocalDataAuthStatisticsResp();
        statisticsDataAuthResp.setFinishAuthCount(finishAuthCount);
        statisticsDataAuthResp.setUnFinishAuthCount(unFinishAuthCount);
        return JsonResponse.success(statisticsDataAuthResp);
    }


    /**
     * 数据授权动作 (1: 同意; 2: 拒绝)
     */
    @ApiOperation(value = "数据授权同意、拒绝")
    @PostMapping("/replyDataAuth")
    public JsonResponse replyDataAuth(@RequestBody @Validated AuthDataActionReq req){
        int action = req.getAction();
        switch (action){
            case 1://同意
                localDataAuthService.agreeAuth(req.getAuthId());
                break;
            case 2://拒绝
                localDataAuthService.refuseAuth(req.getAuthId());
                break;
            default:
                throw new ArgumentException();
        }
        return JsonResponse.success();
    }



    /**
     * 授权申请查看
     */
    @ApiOperation(value = "授权申请查看")
    @GetMapping("/dataAuthDetail")
    public JsonResponse<LocalDataAuthDetailResp> getDataAuthDetail(@ApiParam(name = "authId",value = "授权申请数据id", type = "string", required = true) @RequestParam String authId){

        LocalDataAuthDetail localDataAuthDetail = localDataAuthService.detail(authId);
        LocalDataAuthDetailResp resp = LocalDataAuthDetailResp.from(localDataAuthDetail);
        return JsonResponse.success(resp);
    }























}
