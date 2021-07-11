package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.resp.GlobalPowerPageResp;
import com.platon.rosettanet.admin.service.GlobalPowerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/12 2:50
 * @Version
 * @Desc
 */

@RestController
public class GlobalPowerController {


    @Resource
    private GlobalPowerService globalPowerService;

    /**
     * 展示数据列表，带分页
     */
    public JsonResponse<GlobalPowerPageResp> page(int pageNum, int pageSize){
        Page<GlobalPower> globalPowerPage = globalPowerService.listGlobalPower(pageNum, pageSize,null);
        List<GlobalPowerPageResp> respList = globalPowerPage.getResult().stream()
                .map(GlobalPowerPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalPowerPage,respList);
    }

    /**
     * 根据关键字查询全网算力信息
     */
    public JsonResponse<GlobalPowerPageResp> globalPowerListByKeyWord(int pageNum, int pageSize,String keyword){
        Page<GlobalPower> globalPowerPage = globalPowerService.listGlobalPower(pageNum, pageSize,keyword);
        List<GlobalPowerPageResp> respList = globalPowerPage.getResult().stream()
                .map(GlobalPowerPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(globalPowerPage,respList);
    }
}
