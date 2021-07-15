package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.GlobalDataPageResp;
import com.platon.rosettanet.admin.service.GlobalDataService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("metaDataList")
    public JsonResponse<GlobalDataPageResp> page(int pageNum, int pageSize){
        Page<GlobalDataFile> globalDataFilePage = globalDataService.listDataFile(pageNum, pageSize,null);
        List<GlobalDataPageResp> respList = globalDataFilePage.getResult().stream()
                .map(GlobalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalDataFilePage,respList);
    }

    /**
     * 根据关键字查询元数据列表摘要信息
     */
    @GetMapping("metaDataListByKeyWord")
    public JsonResponse<GlobalDataPageResp> metaDataListByKeyWord(int pageNum, int pageSize,String keyword){
        Page<GlobalDataFile> globalDataFilePage = globalDataService.listDataFile(pageNum, pageSize,keyword);
        List<GlobalDataPageResp> respList = globalDataFilePage.getResult().stream()
                .map(GlobalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalDataFilePage,respList);
    }

    /**
     * 查看数据详情
     */
    @GetMapping("metaDataInfo")
    public JsonResponse<GlobalDataFileDetail> detail(@Validated @NotBlank(message = "metaDataId不为空") String metaDataId){
        GlobalDataFileDetail detail = globalDataService.detail(metaDataId);
        return JsonResponse.success(detail);
    }


}
