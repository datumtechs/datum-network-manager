package com.platon.rosettanet.admin.controller.node;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.common.util.NameUtil;
import com.platon.rosettanet.admin.constant.ControllerConstants;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.*;
import com.platon.rosettanet.admin.dto.resp.AvailableStatusResp;
import com.platon.rosettanet.admin.dto.resp.LocalDataNodeQueryResp;
import com.platon.rosettanet.admin.service.DataNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lyf
 * @Description 数据节点控制层
 * @date 2021/7/8 17:10
 */
@RequestMapping("/api/v1/node/datanode/")
@RestController
@Api(tags = "数据节点接口")
public class DataNodeController {

    @Resource
    private DataNodeService dataNodeService;

    /**
     * @param req 节点分页查询请求类
     * @return
     */
    @ApiOperation(value = "数据节点分页查询", httpMethod = "POST")
    @PostMapping("listNode")
    public JsonResponse <List<LocalDataNodeQueryResp>>listNode(@Validated @RequestBody NodePageReq req) {
        Page<DataNode> dataNodes = dataNodeService.listNode(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        List<DataNode> dataList = dataNodes.getResult();
        List<LocalDataNodeQueryResp> respList = dataList.stream().map(LocalDataNodeQueryResp::convert).collect(Collectors.toList());
        return JsonResponse.page(dataNodes, respList);
    }

    /**
     * 新增数据节点
     *
     * @param dataNodeAddReq
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "新增数据节点", httpMethod = "POST")
    @PostMapping("addDataNode")
    public JsonResponse addDataNode(@Validated @RequestBody DataNodeAddReq dataNodeAddReq) throws Exception {
        //判断数据节点名称是否合法
        if(!NameUtil.isValidName(dataNodeAddReq.getNodeName())){
            return JsonResponse.fail("数据节点名称不合法");
        }
        DataNode dataNode = new DataNode();
        BeanUtils.copyProperties(dataNodeAddReq, dataNode);
        dataNode.setHostName(dataNodeAddReq.getNodeName());
        if (!dataNodeService.checkDataNodeName(dataNode)) {
            return JsonResponse.fail("数据节点名称已存在");
        }
        dataNodeService.addDataNode(dataNode);
        return JsonResponse.success();
    }

    /**
     * 校验数据节点名称是否可用
     *
     * @param checkDataNodeNameReq
     * @return
     */
    @ApiOperation(value = "校验数据节点名称是否可用", httpMethod = "POST")
    @PostMapping("checkDataNodeName")
    public JsonResponse <AvailableStatusResp> checkDataNodeName(@Validated @RequestBody CheckDataNodeNameReq checkDataNodeNameReq) {
        DataNode dataNode = new DataNode();
        dataNode.setHostName(checkDataNodeNameReq.getNodeName());
        AvailableStatusResp resp = new AvailableStatusResp();
        if (dataNodeService.checkDataNodeName(dataNode)) {
            resp.setStatus(ControllerConstants.STATUS_AVAILABLE);
        } else {
            resp.setStatus(ControllerConstants.STATUS_NOT_AVAILABLE);
        }
        return JsonResponse.success(resp);
    }

    /**
     * 修改数据节点
     *
     * @param dataNodeUpdateReq
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改数据节点", httpMethod = "POST")
    @PostMapping("updateDataNode")
    public JsonResponse updateDataNode(@Validated @RequestBody DataNodeUpdateReq dataNodeUpdateReq) throws Exception {
        DataNode dataNode = new DataNode();
        BeanUtils.copyProperties(dataNodeUpdateReq, dataNode);
        dataNodeService.updateDataNode(dataNode);
        return JsonResponse.success();
    }

    /**
     * 删除数据节点
     *
     * @param dataNodeDeleteReq
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除数据节点", httpMethod = "POST")
    @PostMapping("deleteDataNode")
    public JsonResponse deleteDataNode(@Validated @RequestBody DataNodeDeleteReq dataNodeDeleteReq) throws Exception {
        dataNodeService.deleteDataNode(dataNodeDeleteReq.getNodeId());
        return JsonResponse.success();
    }

}
