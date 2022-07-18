package com.platon.datum.admin.controller.data;

import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.ArgumentException;
import com.platon.datum.admin.dao.entity.DataAuth;
import com.platon.datum.admin.dao.entity.DataAuthDetail;
import com.platon.datum.admin.service.DataAuthService;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.AuthDataActionReq;
import com.platon.datum.admin.dto.req.AuthPageReq;
import com.platon.datum.admin.dto.resp.MetaDataAuthDetailResp;
import com.platon.datum.admin.dto.resp.MetaDataAuthPageResp;
import com.platon.datum.admin.dto.resp.MetaDataAuthStatisticsResp;
import com.platon.datum.admin.enums.DtoAuthStatusEnum;
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
 * @deprecated v0.4.0废弃，取而代之的是数据凭证
 */


@Deprecated
@Api(tags = "数据授权")
@Slf4j
//@RestController
@RequestMapping("/api/v1/data/")
public class DataAuthController {

    @Resource
    private DataAuthService dataAuthService;

    /**
     * 授权数据列表，带分页
     */
    @ApiOperation(value = "授权数据列表分页查询")
    @PostMapping("/listLocalDataAuth")
    public JsonResponse<List<MetaDataAuthPageResp>> listLocalDataAuth(@RequestBody @Validated AuthPageReq req){
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
        Page<DataAuth> dataAuthPage = dataAuthService.listLocalDataAuth(req.getPageNumber(), req.getPageSize(), status, req.getKeyWord());
        List<MetaDataAuthPageResp> localDataAuthPageList = dataAuthPage.getResult().stream().map(MetaDataAuthPageResp::from).collect(Collectors.toList());
        return JsonResponse.page(dataAuthPage, localDataAuthPageList);
    }



    /**
     * 授权数据数量统计
     */
    @ApiOperation(value = "授权数据数量统计")
    @GetMapping("/dataAuthStatistics")
    public JsonResponse<MetaDataAuthStatisticsResp> dataAuthStatistics(){

        int finishAuthCount = dataAuthService.selectFinishAuthCount();
        int unFinishAuthCount = dataAuthService.selectUnfinishAuthCount();
        MetaDataAuthStatisticsResp statisticsDataAuthResp = new MetaDataAuthStatisticsResp();
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
                dataAuthService.agreeAuth(req.getAuthId());
                break;
            case 2://拒绝
                dataAuthService.refuseAuth(req.getAuthId());
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
    public JsonResponse<MetaDataAuthDetailResp> getDataAuthDetail(@ApiParam(name = "authId",value = "授权申请数据id", type = "string", required = true) @RequestParam String authId){

        DataAuthDetail dataAuthDetail = dataAuthService.detail(authId);
        MetaDataAuthDetailResp resp = MetaDataAuthDetailResp.from(dataAuthDetail);
        return JsonResponse.success(resp);
    }























}
