package com.platon.datum.admin.controller;

import com.platon.datum.admin.dao.entity.SysConfig;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.SystemAddConfigReq;
import com.platon.datum.admin.dto.req.SystemDeleteConfigReq;
import com.platon.datum.admin.dto.req.SystemGetConfigByKeyReq;
import com.platon.datum.admin.dto.req.SystemUpdateConfigReq;
import com.platon.datum.admin.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2022/4/2 16:47
 * @Version
 * @Desc
 */


@Api(tags = "系统相关")
@Slf4j
@RestController
@RequestMapping("/api/v1/system/")
public class SystemController extends BaseController {


    @Resource
    private SysConfigService sysConfigService;

    /**
     * metaMask所需配置
     */
    @ApiOperation(value = "metaMask所需配置")
    @GetMapping("/getMetaMaskConfig")
    public JsonResponse<List<SysConfig>> getMetaMaskConfig() {
        List<SysConfig> metaMaskConfig = sysConfigService.getMetaMaskConfig();
        return JsonResponse.success(metaMaskConfig);
    }


    /**
     * 查询系统配置的key
     */
    @ApiOperation(value = "查询系统配置的key")
    @GetMapping("/getSystemConfigKey")
    public JsonResponse<Map<String, String>> getSystemConfigKey() {
        Map<String, String> systemConfigKey = sysConfigService.getSystemConfigKey();
        return JsonResponse.success(systemConfigKey);
    }

    /**
     * 查询系统配置
     */
    @ApiOperation(value = "查询配置列表")
    @GetMapping("/getAllConfig")
    public JsonResponse<List<SysConfig>> getAllConfig() {
        List<SysConfig> allValidConfig = sysConfigService.getAllValidConfig();
        return JsonResponse.success(allValidConfig);
    }

    /**
     * 根据key查询配置
     */
    @ApiOperation(value = "根据key查询配置")
    @PostMapping("/getConfigByKey")
    public JsonResponse<SysConfig> getConfigByKey(@RequestBody SystemGetConfigByKeyReq req) {
        SysConfig config = sysConfigService.getConfig(req.getKey());
        return JsonResponse.success(config);
    }

    /**
     * 新增自定义配置
     */
    @ApiOperation(value = "新增自定义配置")
    @PostMapping("/addCustomConfig")
    public JsonResponse<SysConfig> addConfig(@RequestBody SystemAddConfigReq req) {
        SysConfig config = new SysConfig();
        config.setKey(req.getKey());
        config.setValue(req.getValue());
        config.setDesc(req.getDesc());
        config.setStatus(SysConfig.StatusEnum.VALID.getStatus());
        SysConfig resp = sysConfigService.addConfig(config);
        return JsonResponse.success(resp);
    }

    /**
     * 删除系统配置
     */
    @ApiOperation(value = "删除系统配置")
    @PostMapping("/deleteCustomConfig")
    public JsonResponse deleteConfig(@RequestBody SystemDeleteConfigReq req) {
        sysConfigService.deleteConfig(req.getKey());
        return JsonResponse.success();
    }

    /**
     * 更新系统配置
     */
    @ApiOperation(value = "更新系统配置")
    @PostMapping("/updateValueByKey")
    public JsonResponse<SysConfig> updateValueByKey(@RequestBody SystemUpdateConfigReq req) {
        SysConfig config = sysConfigService.updateValueByKey(req.getKey(), req.getValue());
        return JsonResponse.success(config);
    }
}
