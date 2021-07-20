package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dto.CommonPageReq;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.GlobalDataMetaDataListByKeyWordReq;
import com.platon.rosettanet.admin.dto.resp.GlobalDataDetailResp;
import com.platon.rosettanet.admin.dto.resp.GlobalDataPageResp;
import com.platon.rosettanet.admin.service.GlobalDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/8 23:14
 * @Version
 * @Desc 我的数据
 */


@Api(tags = "数据中心")
@RestController
@RequestMapping("/api/v1/resource/datacenter/")
public class GlobalDataController {

    @Resource
    private GlobalDataService globalDataService;

    /**
     * 展示数据列表，带分页
     */
    @ApiOperation(value = "数据列表分页查询")
    @PostMapping("metaDataList")
    public JsonResponse<List<GlobalDataPageResp>> page(@RequestBody @Validated CommonPageReq req){
        Page<GlobalDataFile> globalDataFilePage = globalDataService.listDataFile(req.getPageNumber(), req.getPageSize(),null);
        List<GlobalDataPageResp> respList = globalDataFilePage.getResult().stream()
                .map(GlobalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalDataFilePage,respList);
    }

    /**
     * 根据关键字查询元数据列表摘要信息
     */
    @ApiOperation(value = "数据列表关键字查询")
    @PostMapping("metaDataListByKeyWord")
    public JsonResponse<List<GlobalDataPageResp>> metaDataListByKeyWord(@RequestBody @Validated GlobalDataMetaDataListByKeyWordReq req){
        Page<GlobalDataFile> globalDataFilePage = globalDataService.listDataFile(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        List<GlobalDataPageResp> respList = globalDataFilePage.getResult().stream()
                .map(GlobalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalDataFilePage,respList);
    }

    /**
     * 查看数据详情
     */
    @ApiOperation(value = "查看数据详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "metaDataId",value = "元数据ID",required = true,paramType = "query",example = "mataData111"),
    })
    @GetMapping("metaDataInfo")
    public JsonResponse<GlobalDataDetailResp> detail(@Validated @NotBlank(message = "metaDataId不为空") String metaDataId){
        GlobalDataFileDetail detail = globalDataService.detail(metaDataId);
        GlobalDataDetailResp resp = GlobalDataDetailResp.from(detail);
        return JsonResponse.success(resp);
    }


}
