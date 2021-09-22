package com.platon.metis.admin.controller.system;

import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.resp.SystemQueryBaseInfoResp;
import com.platon.metis.admin.service.LocalOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "当前组织信息")
@RequestMapping("/api/v1/system/")
@RestController
public class SystemBaseInfoController {

    @Resource
    private LocalOrgService localOrgService;

    /**
     * 登录后查询出当前组织信息
     * @return
     */
    @ApiOperation(value = "查询出当前组织信息")
    @GetMapping("queryBaseInfo")
    public JsonResponse<SystemQueryBaseInfoResp> queryBaseInfo(){
        LocalOrg localOrg = localOrgService.getLocalOrg();
        SystemQueryBaseInfoResp resp = SystemQueryBaseInfoResp.from(localOrg);
        return JsonResponse.success(resp);
    }
}
