package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.SystemQueryBaseInfoResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:54
 * @Version
 * @Desc
 */

@RequestMapping("/api/v1/system/")
@RestController
public class SystemBaseInfoController {

    /**
     * 登录后查询出当前组织信息
     * @return
     */
    @GetMapping("queryBaseInfo")
    public JsonResponse<SystemQueryBaseInfoResp> queryBaseInfo(){

        return JsonResponse.success();
    }
}
