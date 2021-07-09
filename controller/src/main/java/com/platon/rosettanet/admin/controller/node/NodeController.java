package com.platon.rosettanet.admin.controller.node;

import com.platon.rosettanet.admin.dto.JsonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liushuyu
 * @Date 2021/7/2 16:18
 * @Version
 * @Desc
 */

@RequestMapping("/api/v1/node/corenode/")
@RestController
public class NodeController {

    @PostMapping("connectNode")
    public JsonResponse connectNode(){
        return JsonResponse.success();
    }
}
