package com.platon.metis.admin.controller.token;

import com.github.pagehelper.Page;
import com.platon.metis.admin.controller.BaseController;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.dao.entity.SysConfig;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenGetPublishConfigReq;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenPageReq;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenPublishReq;
import com.platon.metis.admin.dto.req.NoAttributeDataTokenUpReq;
import com.platon.metis.admin.dto.resp.NoAttributeDataTokenGetPublishConfigResp;
import com.platon.metis.admin.dto.resp.NoAttributeDataTokenGetUpConfigResp;
import com.platon.metis.admin.service.NoAttributeDataTokenService;
import com.platon.metis.admin.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/api/v1/dataToken/")
public class NoAttributeDataTokenController extends BaseController {

    @Resource
    private NoAttributeDataTokenService noAttributeDataTokenService;
    @Resource
    private SysConfigService sysConfigService;


    /**
     * 授权数据列表，带分页
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/page")
    public JsonResponse<List<DataToken>> page(@RequestBody @Validated NoAttributeDataTokenPageReq req, HttpSession session) {
        String address = getCurrentUserAddress(session);
        Page<DataToken> page = noAttributeDataTokenService.page(req.getPageNumber(), req.getPageSize(), req.getStatus(), address);
        return JsonResponse.page(page);
    }

    /**
     * 查询dex链接地址
     */
    @ApiOperation(value = "查询dex链接地址")
    @PostMapping("/getDexWebUrl")
    public JsonResponse<String> getDexWebUrl() {
        String value = sysConfigService.getConfig(SysConfig.KeyEnum.DEX_WEB_URL.getKey()).getValue();
        return JsonResponse.success(value);
    }

    /**
     * 获取发布凭证需要的配置
     */
    @ApiOperation(value = "获取发布凭证需要的配置")
    @GetMapping("/getPublishConfig")
    public JsonResponse<NoAttributeDataTokenGetPublishConfigResp> getPublishConfig(@RequestBody NoAttributeDataTokenGetPublishConfigReq req) {
        Integer dataTokenId = req.getDataTokenId();
        DataToken dataToken = null;
        if (dataTokenId != null) {
            dataToken = noAttributeDataTokenService.getDataTokenById(dataTokenId);
        }
        NoAttributeDataTokenGetPublishConfigResp resp = new NoAttributeDataTokenGetPublishConfigResp();
        SysConfig config = sysConfigService.getConfig(SysConfig.KeyEnum.DATA_TOKEN_FACTORY_ADDRESS.getKey());
        resp.setDataTokenFactory(config.getValue());
        resp.setDataToken(dataToken);
        return JsonResponse.success(resp);
    }


    /**
     * 发布凭证
     */
    @ApiOperation(value = "发布凭证")
    @PostMapping("/publish")
    public JsonResponse publish(@RequestBody @Validated NoAttributeDataTokenPublishReq req, HttpSession session) {
        String address = getCurrentUserAddress(session);

        DataToken dataToken = new DataToken();
        dataToken.setName(req.getName());
        dataToken.setSymbol(req.getSymbol());
        dataToken.setTotal(req.getTotal());
        dataToken.setDesc(req.getDesc());
        dataToken.setMetaDataId(req.getMetaDataId());
        dataToken.setPublishHash(req.getHash());
        dataToken.setOwner(address);
        dataToken.setPublishHash(req.getHash());
        dataToken.setPublishNonce(req.getNonce());
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
        SysConfig config = sysConfigService.getConfig(SysConfig.KeyEnum.DEX_ROUTER_ADDRESS.getKey());
        resp.setRouterToken(config.getValue());
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
        dataToken.setUpNonce(req.getNonce());
        noAttributeDataTokenService.up(dataToken);
        return JsonResponse.success();
    }

}
