package com.platon.metis.admin.controller.resource;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ApplicationException;
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
import com.platon.metis.admin.service.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc 数据授权
 */


@Api(tags = "数据授权")
@RestController
@RequestMapping("/api/v1/resource/auth/")
public class LocalDataAuthController {

    @Resource
    private LocalDataAuthService localDataAuthService;

    /**
     * 授权数据列表，带分页
     */
    @ApiOperation(value = "授权数据列表分页查询")
    @PostMapping("authDataList")
    public JsonResponse<List<LocalDataAuthPageResp>> page(@RequestBody @Validated AuthPageReq req){
        if(req.getStatus() != DtoAuthStatusEnum.AUTH_UNDEFINED.getStatus() && req.getStatus() != DtoAuthStatusEnum.AUTH_UNFINISH.getStatus() &&
           req.getStatus() != DtoAuthStatusEnum.AUTH_FINISH.getStatus()){
            throw new ServiceException("入参授权状态有误，请核对必须为0：未定义，1:待授权数据，2:已授权数据");
        }
        Page<LocalDataAuth> localDataAuthPage = localDataAuthService.listLocalDataAuth(req.getPageNumber(), req.getPageSize(), req.getStatus(), req.getKeyWord());
        List<LocalDataAuthPageResp> localDataAuthPageList = localDataAuthPage.getResult().stream().map(LocalDataAuthPageResp::from).collect(Collectors.toList());
        return JsonResponse.page(localDataAuthPage, localDataAuthPageList);
    }



    /**
     * 授权数据数量统计
     */
    @ApiOperation(value = "授权数据数量统计")
    @GetMapping("authDataStatistics")
    public JsonResponse<LocalDataAuthStatisticsResp> authDataStatistics(){

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
    @PostMapping("actionAuthData")
    public JsonResponse actionAuthData(@RequestBody @Validated AuthDataActionReq req){
        int action = req.getAction();
        int count = 0;
        switch (action){
            case 1://同意
                count = localDataAuthService.agreeAuth(req.getAuthId());
                break;
            case 2://拒绝
                count = localDataAuthService.refuseAuth(req.getAuthId());
                break;
            default:
                throw new ApplicationException(StrUtil.format("请输入正确的action[1: 同意; 2: 拒绝]：{}",action));
        }
        if(count <= 0){
            JsonResponse.fail("操作失败");
        }
        return JsonResponse.success();
    }



    /**
     * 授权申请查看
     */
    @ApiOperation(value = "授权申请查看")
    @GetMapping("authDataDetail")
    public JsonResponse<LocalDataAuthDetailResp> authDataDetail(@ApiParam(name = "authId",value = "授权申请数据id", type = "string", required = true) @RequestParam String authId){

        LocalDataAuthDetail localDataAuthDetail = localDataAuthService.detail(authId);
        LocalDataAuthDetailResp resp = LocalDataAuthDetailResp.from(localDataAuthDetail);
        return JsonResponse.success(resp);
    }























}
