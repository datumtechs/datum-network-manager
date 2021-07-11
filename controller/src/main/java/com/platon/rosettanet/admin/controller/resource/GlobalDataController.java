package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.GlobalDataDetailReq;
import com.platon.rosettanet.admin.dto.resp.GlobalDataPageResp;
import com.platon.rosettanet.admin.service.GlobalDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/8 23:14
 * @Version
 * @Desc 我的数据
 */



@RestController
public class GlobalDataController {

    @Resource
    private GlobalDataService globalDataService;

    /**
     * 展示数据列表，带分页
     */
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
    public JsonResponse<GlobalDataFileDetail> detail(@RequestBody @Validated GlobalDataDetailReq req){
        GlobalDataFileDetail detail = globalDataService.detail(req.getMetaDataId());
        return JsonResponse.success(detail);
    }


}
