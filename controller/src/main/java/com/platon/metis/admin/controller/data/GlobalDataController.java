package com.platon.metis.admin.controller.data;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.dto.StatsTrendDTO;
import com.platon.metis.admin.dao.entity.GlobalDataFile;
import com.platon.metis.admin.dto.CommonPageReq;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.GlobalDataMetaDataListByKeyWordReq;
import com.platon.metis.admin.dto.resp.GlobalDataDetailResp;
import com.platon.metis.admin.dto.resp.GlobalDataPageResp;
import com.platon.metis.admin.service.GlobalDataService;
import com.platon.metis.admin.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/data/")
@Slf4j
public class GlobalDataController {

    @Resource
    private GlobalDataService globalDataService;
    @Resource
    private IndexService indexService;


    @ApiOperation(value = "全网数据日走势图")
    @GetMapping("globalDataFileStatsTrendDaily")
    public JsonResponse<List<StatsTrendDTO>> globalDataFileStatsTrendDaily(){
        List<StatsTrendDTO> list = indexService.listGlobalDataFileStatsTrendDaily();
        return JsonResponse.success(list);
    }


    /**
     * 展示数据列表，带分页
     */
    @ApiOperation(value = "数据列表分页查询")
    @PostMapping("listGlobalMetaData")
    public JsonResponse<List<GlobalDataPageResp>> listGlobalMetaData(@RequestBody @Validated CommonPageReq req){
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
    @PostMapping("listGlobalMetaDataByKeyword")
    public JsonResponse<List<GlobalDataPageResp>> listGlobalMetaDataByKeyword(@RequestBody @Validated GlobalDataMetaDataListByKeyWordReq req){
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
            @ApiImplicitParam(name = "metaDataId",value = "metaDataId:0x",required = true,paramType = "query",example = "metadata:0x245631b081845b7af31c9522b7b18712bf92d1f33111fc39a6aea1039add097d"),
    })
    @GetMapping("globalMetaDataDetail")
    public JsonResponse<GlobalDataDetailResp> globalMetaDataDetail(@Validated @NotBlank(message = "metaDataId不为空") String metaDataId){
        GlobalDataFile detail = globalDataService.findGlobalDataFile(metaDataId);
        GlobalDataDetailResp resp = GlobalDataDetailResp.from(detail);
        return JsonResponse.success(resp);
    }
}
