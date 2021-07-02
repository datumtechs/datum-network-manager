package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dto.JsonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:13
 * @Version
 * @Desc
 */

@RestController
@RequestMapping("/api/v1/system/index/")
public class IndexController {


    @PostMapping("overview.json")
    public JsonResponse overview(){

        return JsonResponse.success();
    }
}
