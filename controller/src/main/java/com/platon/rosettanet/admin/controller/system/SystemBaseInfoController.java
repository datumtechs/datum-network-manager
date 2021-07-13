package com.platon.rosettanet.admin.controller.system;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.SystemQueryBaseInfoResp;
import com.platon.rosettanet.admin.service.LocalOrgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:54
 * @Version
 * @Desc
 */

@RequestMapping("/api/v1/system/")
@RestController
public class SystemBaseInfoController {

    @Resource
    private LocalOrgService localOrgService;

    /**
     * 登录后查询出当前组织信息
     * @return
     */
    @GetMapping("queryBaseInfo")
    public JsonResponse<SystemQueryBaseInfoResp> queryBaseInfo(){
        LocalOrg localOrg = localOrgService.getLocalOrg();
        SystemQueryBaseInfoResp resp = SystemQueryBaseInfoResp.from(localOrg);
        return JsonResponse.success(resp);
    }
}
