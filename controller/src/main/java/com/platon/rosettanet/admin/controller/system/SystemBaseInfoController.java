package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.SystemQueryBaseInfoResp;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("queryBaseInfo")
    public JsonResponse<SystemQueryBaseInfoResp> queryBaseInfo(){

        return JsonResponse.success();
    }
}
