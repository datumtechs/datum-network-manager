package com.platon.metis.admin.controller.token;

import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ArgumentException;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.auth.AuthDataActionReq;
import com.platon.metis.admin.dto.req.auth.AuthPageReq;
import com.platon.metis.admin.dto.resp.auth.LocalDataAuthPageResp;
import com.platon.metis.admin.dto.resp.auth.LocalDataAuthStatisticsResp;
import com.platon.metis.admin.enums.DtoAuthStatusEnum;
import com.platon.metis.admin.service.LocalDataAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2022/3/3 0:13
 * @Version
 * @Desc
 */

@Api(tags = "无属性数据凭证")
@Slf4j
//@RestController
@RequestMapping("/api/v1/data/")
public class NoDiffDataTokenController {

    @Resource
    private LocalDataAuthService localDataAuthService;

    /**
     * 授权数据列表，带分页
     */
    @ApiOperation(value = "查询无属性数据凭证列表")
    @PostMapping("listModel")
    public JsonResponse<List<LocalDataAuthPageResp>> listModel(@RequestBody @Validated AuthPageReq req){
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
    @ApiOperation(value = "无属性数据凭证交易走势图")
    @PostMapping("dataAuthStatistics")
    public JsonResponse<LocalDataAuthStatisticsResp> dataAuthStatistics(){

        int finishAuthCount = localDataAuthService.selectFinishAuthCount();
        int unFinishAuthCount = localDataAuthService.selectUnfinishAuthCount();
        LocalDataAuthStatisticsResp statisticsDataAuthResp = new LocalDataAuthStatisticsResp();
        statisticsDataAuthResp.setFinishAuthCount(finishAuthCount);
        statisticsDataAuthResp.setUnFinishAuthCount(unFinishAuthCount);
        return JsonResponse.success(statisticsDataAuthResp);
    }

}
