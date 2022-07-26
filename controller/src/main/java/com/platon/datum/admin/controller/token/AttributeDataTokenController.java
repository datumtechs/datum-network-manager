package com.platon.datum.admin.controller.token;

import com.github.pagehelper.Page;
import com.platon.datum.admin.controller.BaseController;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.dao.entity.AttributeDataTokenInventory;
import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.AttributeDataTokenGetPublishConfigResp;
import com.platon.datum.admin.service.AttributeDataTokenInventoryService;
import com.platon.datum.admin.service.AttributeDataTokenService;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/3/3 0:13
 * @Version
 */

@Api(tags = "有属性数据凭证")
@Slf4j
@RestController
@RequestMapping("/api/v1/attributeDataToken/")
public class AttributeDataTokenController extends BaseController {

    @Resource
    private AttributeDataTokenService attributeDataTokenService;
    @Resource
    private AttributeDataTokenInventoryService attributeDataTokenInventoryService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private IpfsOpService ipfsOpService;

    @ApiOperation(value = "获取发布凭证需要的配置")
    @PostMapping("/getPublishConfig")
    public JsonResponse<AttributeDataTokenGetPublishConfigResp> getPublishConfig(@RequestBody AttributeDataTokenGetPublishConfigReq req, HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        Integer dataTokenId = req.getDataTokenId();
        AttributeDataToken dataToken = null;
        if (dataTokenId != null) {
            dataToken = attributeDataTokenService.getDataTokenById(dataTokenId, currentUserAddress);
        }
        AttributeDataTokenGetPublishConfigResp resp = new AttributeDataTokenGetPublishConfigResp();
        SysConfig config = sysConfigService.getConfig(SysConfig.KeyEnum.ATTRIBUTE_DATA_TOKEN_FACTORY_ADDRESS.getKey());
        resp.setFactoryAddress(config.getValue());
        resp.setDataToken(dataToken);
        return JsonResponse.success(resp);
    }

    @ApiOperation(value = "发布凭证")
    @PostMapping("/publish")
    public JsonResponse<Integer> publish(@RequestBody @Validated AttributeDataTokenPublishReq req, HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);

        AttributeDataToken dataToken = new AttributeDataToken();
        dataToken.setName(req.getName());
        dataToken.setSymbol(req.getSymbol());
        dataToken.setMetaDataDbId(req.getMetaDataDbId());
        dataToken.setOwner(currentUserAddress);
        dataToken.setPublishHash(req.getHash());
        dataToken.setPublishNonce(req.getNonce());
        Integer id = attributeDataTokenService.publish(dataToken);
        return JsonResponse.success(id);
    }

    /**
     * 修改凭证状态
     */
    @ApiOperation(value = "修改凭证状态")
    @PostMapping("/updateDataTokenStatus")
    public JsonResponse updateDataTokenStatus(HttpSession session, @RequestBody @Validated AttributeDataTokenUpdateStatusReq req) {
        String currentUserAddress = getCurrentUserAddress(session);
        attributeDataTokenService.updateStatus(req.getDataTokenId(), req.getStatus(), currentUserAddress);
        return JsonResponse.success();
    }

    /**
     * 1.有属性数据凭证列表（带分页）
     * 2.根据凭证合约名称模糊查询出有属性数据凭证列表（序号，凭证合约名称，数据名称，凭证合约符号，合约地址）
     */
    @ApiOperation(value = "查询凭证列表")
    @PostMapping("/page")
    public JsonResponse<List<AttributeDataToken>> page(@RequestBody @Validated AttributeDataTokenPageReq req, HttpSession session) {
        String currentUserAddress = getCurrentUserAddress(session);
        Page<AttributeDataToken> page = attributeDataTokenService.page(req.getPageNumber(), req.getPageSize(), req.getKeyword(), currentUserAddress);
        return JsonResponse.page(page);
    }

    /**
     * 绑定元数据
     */
    @ApiOperation(value = "绑定元数据")
    @PostMapping("/bindMetaData")
    public JsonResponse bindMetaData(HttpSession session, @RequestBody @Validated AttributeDataTokenBindMetaDataReq req) {
        String currentUserAddress = getCurrentUserAddress(session);
        attributeDataTokenService.bindMetaData(req.getDataTokenId(), req.getSign(), currentUserAddress);
        return JsonResponse.success();
    }

    /**
     * ===============凭证库存相关接口================
     */

    /**
     * 1.1.创建凭证库存--上传图片接口，返回ipfs路径
     */
    @ApiOperation(value = "上传图片接口")
    @PostMapping("/inventoryUpLoad")
    public JsonResponse<String> inventoryUpLoad(@RequestBody MultipartFile file) {
        String ipfsPath = ipfsOpService.saveFile(file);
        return JsonResponse.success(ipfsPath);
    }

    /**
     * 1.2.创建成功后通知后台刷新库存信息
     */
    @ApiOperation(value = "刷新库存信息5次")
    @PostMapping("/refreshInventory5")
    public JsonResponse<String> refreshInventory5(@RequestBody @Validated AttributeDataTokenRefreshInventory5Req req) {
        attributeDataTokenInventoryService.refresh5(req.getTokenAddress());
        return JsonResponse.success();
    }

    @ApiOperation(value = "刷新指定tokenId库存信息")
    @PostMapping("/refreshInventoryByTokenId")
    public JsonResponse<String> refreshInventoryByTokenId(@RequestBody @Validated AttributeDataTokenRefreshInventoryByTokenIdReq req) {
        attributeDataTokenInventoryService.refreshByTokenId(req.getTokenAddress(), req.getTokenId());
        return JsonResponse.success();
    }

    /**
     * 2.库存列表（带分页）--支持凭证名称和tokenid模糊查询（序号，凭证名称，数据名称，凭证ID，使用场景，凭证有效期）
     */
    @ApiOperation(value = "获取dataToken库存列表")
    @PostMapping("/getDataTokenInventoryPage")
    public JsonResponse<AttributeDataTokenInventory> getDataTokenInventoryPage(@RequestBody @Validated AttributeDataTokenGetDataTokenInventoryPageReq req) {
        Page<AttributeDataTokenInventory> page = attributeDataTokenInventoryService.page(req.getPageNumber(),
                req.getPageSize(),
                req.getKeyword(),
                req.getDataTokenAddress());
        return JsonResponse.page(page);
    }

    /**
     * 3.查看库存详情--（凭证描述，名字，tokenid，使用场景，合约名称，合约地址，持有用户，元数据ID，创建时间，有效期，上架平台）
     */
    @ApiOperation(value = "根据id获取dataToken库存详情")
    @PostMapping("/getDataTokenInventoryDetail")
    public JsonResponse<AttributeDataTokenInventory> getDataTokenInventoryDetail(@RequestBody @Validated AttributeDataTokenGetDataTokenInventoryDetailReq req) {
        AttributeDataTokenInventory inventory = attributeDataTokenInventoryService.getInventoryByDataTokenAddressAndTokenId(
                req.getDataTokenAddress(),
                req.getTokenId());
        return JsonResponse.success(inventory);
    }

}
