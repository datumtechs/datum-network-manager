package com.platon.rosettanet.admin.controller.system;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dto.CommonPageReq;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;
import com.platon.rosettanet.admin.dto.resp.IndexOverviewResp;
import com.platon.rosettanet.admin.service.IndexService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

@RestController
@RequestMapping("/api/v1/system/index/")
public class IndexController {

    @Resource
    private IndexService indexService;


    @PostMapping("overview")
    public JsonResponse<IndexOverviewResp> overview(){
        return JsonResponse.success();
    }

    /*@PostMapping("nodeList.json")
    public JsonResponse<IndexNodeListResp> nodeList(@Validated @RequestBody CommonPageReq req){
        Page<TbPowerNode> nodePage = indexService.nodeList(req.getPageNumber(), req.getPageSize());
        return JsonResponse.page(nodePage);
    }*/
}
