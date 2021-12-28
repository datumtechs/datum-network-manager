package com.platon.metis.admin.controller.node;

import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.NodeNameExists;
import com.platon.metis.admin.common.exception.NodeNameIllegal;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.entity.LocalDataNode;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.*;
import com.platon.metis.admin.dto.resp.AvailableStatusResp;
import com.platon.metis.admin.dto.resp.LocalDataNodeQueryResp;
import com.platon.metis.admin.service.DataNodeService;
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
@RequestMapping("/api/v1/datanode/")
@RestController
@Api(tags = "数据节点管理")
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
        Page<LocalDataNode> localDataNodes = dataNodeService.listNode(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        List<LocalDataNode> dataList = localDataNodes.getResult();
        List<LocalDataNodeQueryResp> respList = dataList.stream().map(LocalDataNodeQueryResp::convert).collect(Collectors.toList());
        return JsonResponse.page(localDataNodes, respList);
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
            throw new NodeNameIllegal();
        }
        LocalDataNode localDataNode = new LocalDataNode();
        BeanUtils.copyProperties(dataNodeAddReq, localDataNode);
        localDataNode.setNodeName(dataNodeAddReq.getNodeName());
        if (!dataNodeService.checkDataNodeName(localDataNode)) {
            throw new NodeNameExists();
        }
        dataNodeService.addDataNode(localDataNode);
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
        LocalDataNode localDataNode = new LocalDataNode();
        localDataNode.setNodeName(checkDataNodeNameReq.getNodeName());
        AvailableStatusResp resp = new AvailableStatusResp();
        if (dataNodeService.checkDataNodeName(localDataNode)) {
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
        LocalDataNode localDataNode = new LocalDataNode();
        BeanUtils.copyProperties(dataNodeUpdateReq, localDataNode);
        dataNodeService.updateDataNode(localDataNode);
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
