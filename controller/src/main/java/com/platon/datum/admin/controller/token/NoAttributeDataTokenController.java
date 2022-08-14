package com.platon.datum.admin.controller.token;

import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.controller.BaseController;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.NoAttributeDataTokenGetPublishConfigResp;
import com.platon.datum.admin.dto.resp.NoAttributeDataTokenGetUpConfigResp;
import com.platon.datum.admin.service.NoAttributeDataTokenService;
import com.platon.datum.admin.service.SysConfigService;
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
        Page<DataToken> page = noAttributeDataTokenService.page(req.getPageNumber(), req.getPageSize(), req.getKeyword(), address);
        return JsonResponse.page(page);
    }

    /**
     * 根据id获取dataToken状态
     */
    @ApiOperation(value = "根据id获取dataToken状态")
    @PostMapping("/getDataTokenStatus")
    public JsonResponse<DataToken> getDataTokenStatus(@RequestBody @Validated NoAttributeDataTokenGetDataTokenStatusReq req) {
        DataToken dataToken = noAttributeDataTokenService.getDataTokenById(req.getId());
        return JsonResponse.success(dataToken);
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
    @PostMapping("/getPublishConfig")
    public JsonResponse<NoAttributeDataTokenGetPublishConfigResp> getPublishConfig(@RequestBody NoAttributeDataTokenGetPublishConfigReq req) {
        Integer id = req.getId();
        DataToken dataToken = null;
        if (id != null) {
            dataToken = noAttributeDataTokenService.getDataTokenById(id);
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
    public JsonResponse<Integer> publish(@RequestBody @Validated NoAttributeDataTokenPublishReq req, HttpSession session) {
        String address = getCurrentUserAddress(session);
        String owner = req.getOwner();
        if (!address.equalsIgnoreCase(owner)) {
            throw new BizException(Errors.SysException, "Current user is not owner!");
        }
        DataToken dataToken = new DataToken();
        dataToken.setName(req.getName());
        dataToken.setSymbol(req.getSymbol());
        dataToken.setInit(req.getInit());
        dataToken.setTotal(req.getTotal());
        dataToken.setDesc(req.getDesc());
        dataToken.setMetaDataDbId(req.getMetaDataDbId());
        dataToken.setOwner(req.getOwner());
        dataToken.setPublishHash(req.getHash());
        dataToken.setPublishNonce(req.getNonce());
        dataToken.setPlaintextFee(req.getPlaintextFee());
        dataToken.setCiphertextFee(req.getCiphertextFee());
        Integer id = noAttributeDataTokenService.publish(dataToken);
        return JsonResponse.success(id);
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

    /**
     * 修改凭证状态
     */
    @ApiOperation(value = "将凭证修改为上架状态")
    @PostMapping("/updateToPrinceSuccess")
    public JsonResponse updateToPrinceSuccess(HttpSession session, @RequestBody @Validated NoAttributeDataTokenUpdateStatusReq req) {
        String currentUserAddress = getCurrentUserAddress(session);
        noAttributeDataTokenService.updateToPrinceSuccess(req.getDataTokenId(), currentUserAddress);
        return JsonResponse.success();
    }

    /**
     * 修改明文和密文消耗量
     */
    @ApiOperation(value = "修改明文和密文消耗量")
    @PostMapping("/updateFee")
    public JsonResponse updateFee(HttpSession session, @RequestBody @Validated NoAttributeDataTokenUpdateFeeReq req) {
        String currentUserAddress = getCurrentUserAddress(session);
        noAttributeDataTokenService.updateFee(
                req.getDataTokenId(),
                req.getNewCiphertextFee(),
                req.getNewPlaintextFee(),
                req.getSign(),
                currentUserAddress);
        return JsonResponse.success();
    }

    /**
     * 绑定元数据
     */
    @ApiOperation(value = "绑定元数据")
    @PostMapping("/bindMetaData")
    public JsonResponse bindMetaData(HttpSession session, @RequestBody @Validated NoAttributeDataTokenBindMetaDataReq req) {
        String currentUserAddress = getCurrentUserAddress(session);
        noAttributeDataTokenService.bindMetaData(req.getDataTokenId(), req.getSign(), currentUserAddress);
        return JsonResponse.success();
    }
}
