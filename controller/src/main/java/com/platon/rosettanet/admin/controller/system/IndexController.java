package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.IndexNodeListResp;
import com.platon.rosettanet.admin.dto.resp.IndexOverviewResp;
import com.platon.rosettanet.admin.service.IndexService;
import org.springframework.web.bind.annotation.GetMapping;
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


    /**
     * 查看当前系统的概览信息,即首页信息，该接口大部分内容为统计信息
     * @return
     */
    @GetMapping("overview")
    public JsonResponse<IndexOverviewResp> overview(){
        return JsonResponse.success();
    }


    /**
     * 即首页信息计算节点列表
     * @return
     */
    @GetMapping("nodeList.json")
    public JsonResponse<IndexNodeListResp> nodeList(){
        return JsonResponse.success();
    }
}
