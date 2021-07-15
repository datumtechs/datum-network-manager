package com.platon.rosettanet.admin.controller.node;

import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.service.NodeService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2021/7/2 16:18
 * @Version
 * @Desc
 */

/**
 * 调度服务管理
 */

@Api(tags = "调度服务管理")
@RequestMapping("/api/v1/node/corenode/")
@RestController
public class NodeController {

    @Resource
    private NodeService nodeService;

    /**
     * 连接调度节点
     * @return
     */
    @PostMapping("connectNode")
    public JsonResponse<String> connectNode(@Validated @NotBlank(message = "ip不能为空") String ip,
                                            @Validated @NotNull(message = "port不能为空") Integer port){
        String status = nodeService.connectNode(ip,port);
        return JsonResponse.success(status);
    }

    /**
     * 通知调度服务，申请准入网络
     */
    @PostMapping("applyJoinNetwork")
    public JsonResponse applyJoinNetwork(){
        nodeService.applyJoinNetwork();
        return JsonResponse.success();
    }

    /**
     * 调用该接口后，其对应的调度服务从网络中退出，无法继续参与隐私网络中的相关任务项
     */
    @PostMapping("cancelJoinNetwork")
    public JsonResponse cancelJoinNetwork(){
        nodeService.cancelJoinNetwork();
        return JsonResponse.success();
    }
}
