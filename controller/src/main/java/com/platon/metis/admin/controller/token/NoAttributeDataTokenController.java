package com.platon.metis.admin.controller.token;

import com.github.pagehelper.Page;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.SysUser;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenPageReq;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenPublishReq;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenUpReq;
import com.platon.metis.admin.dto.resp.NoAttributeDataTokenGetPublishConfigResp;
import com.platon.metis.admin.dto.resp.NoAttributeDataTokenGetUpConfigResp;
import com.platon.metis.admin.service.NoAttributeDataTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/3/3 0:13
 * @Version
 * @Desc
 */

@Api(tags = "无属性数据凭证")
@Slf4j
//@RestController
@RequestMapping("/api/v1/dataToken/")
public class NoAttributeDataTokenController {

    @Resource
    private NoAttributeDataTokenService noAttributeDataTokenService;
    @Value("${dataToken.publish.config.dataTokenFactory}")
    private String dataTokenFactory;
    @Value("${dataToken.up.config.routerToken}")
    private String routerToken;

    /**
     * 授权数据列表，带分页,TODO 需要加上dex地址
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/page")
    public JsonResponse<List<DataToken>> page(@RequestBody @Validated NoAttributeDataTokenPageReq req) {
        Page<DataToken> page = noAttributeDataTokenService.page(req.getPageNumber(),req.getPageSize(),req.getStatus());
        return JsonResponse.page(page);
    }


    /**
     * 获取发布凭证需要的配置
     */
    @ApiOperation(value = "获取发布凭证需要的配置")
    @GetMapping("/getPublishConfig")
    public JsonResponse<NoAttributeDataTokenGetPublishConfigResp> getPublishConfig() {
        NoAttributeDataTokenGetPublishConfigResp resp = new NoAttributeDataTokenGetPublishConfigResp();
        LocalOrg localOrgInfo = LocalOrgCache.getLocalOrgInfo();
        resp.setAgent(localOrgInfo.getCarrierWallet());
        resp.setDataTokenFactory(dataTokenFactory);
        resp.setGas("210000");
        resp.setGasPrice("10000000000");
        return JsonResponse.success(resp);
    }


    /**
     * 发布凭证
     */
    @ApiOperation(value = "发布凭证")
    @PostMapping("/publish")
    public JsonResponse publish(@RequestBody @Validated NoAttributeDataTokenPublishReq req, HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        //获取当前用户钱包地址
        String address = userInfo.getAddress();
        LocalOrg localOrgInfo = LocalOrgCache.getLocalOrgInfo();

        DataToken dataToken = new DataToken();
        dataToken.setName(req.getName());
        dataToken.setSymbol(req.getSymbol());
        dataToken.setTotal(req.getTotal());
        dataToken.setDesc(req.getDesc());
        dataToken.setMetaDataId(req.getMetaDataId());
        dataToken.setPublishHash(req.getHash());
        dataToken.setOwner(address);
        dataToken.setAgent(localOrgInfo.getCarrierWallet());
        noAttributeDataTokenService.publish(dataToken);
        return JsonResponse.success();
    }

    /**
     * 获取上架市场需要的配置
     */
    @ApiOperation(value = "获取上架市场需要的配置")
    @GetMapping("/getUpConfig")
    public JsonResponse<NoAttributeDataTokenGetUpConfigResp> getUpConfig() {
        NoAttributeDataTokenGetUpConfigResp resp = new NoAttributeDataTokenGetUpConfigResp();
        resp.setGas("210000");
        resp.setGasPrice("10000000000");
        resp.setRouterToken(routerToken);
        return JsonResponse.success(resp);
    }

    /**
     * 上架市场
     */
    @ApiOperation(value = "上架市场")
    @PostMapping("/up")
    public JsonResponse up(@RequestBody @Validated NoAttributeDataTokenUpReq req) {
        DataToken dataToken = new DataToken();
        dataToken.setId(req.getDataTokenId());
        dataToken.setUpHash(req.getHash());
        noAttributeDataTokenService.up(dataToken);
        return JsonResponse.success();
    }

}
