package com.platon.datum.admin.controller.node;

import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.NameUtil;
import com.platon.datum.admin.dao.entity.DataNode;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.DataNodeUpdateReq;
import com.platon.datum.admin.dto.req.NodePageReq;
import com.platon.datum.admin.dto.resp.DataNodeQueryResp;
import com.platon.datum.admin.service.DataNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    @PostMapping("/listNode")
    public JsonResponse<List<DataNodeQueryResp>> listNode(@Validated @RequestBody NodePageReq req) {
        Page<DataNode> dataNodes = dataNodeService.listNode(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        List<DataNode> dataList = dataNodes.getResult();
        List<DataNodeQueryResp> respList = dataList.stream().map(DataNodeQueryResp::convert).collect(Collectors.toList());
        return JsonResponse.page(dataNodes, respList);
    }

    /**
     * 修改数据节点名称
     *
     * @param dataNodeUpdateReq
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改数据节点名称", httpMethod = "POST")
    @PostMapping("/updateNodeName")
    public JsonResponse updateNodeName(@Validated @RequestBody DataNodeUpdateReq dataNodeUpdateReq) {
        if (!NameUtil.isValidName(dataNodeUpdateReq.getNodeName())) {
            throw new BizException(Errors.NodeNameIllegal);
        }

        DataNode dataNode = dataNodeService.findLocalDataNodeByName(dataNodeUpdateReq.getNodeName());
        if (dataNode != null) {
            if (StringUtils.equals(dataNode.getNodeId(), dataNodeUpdateReq.getNodeId())) {
                return JsonResponse.success();
            } else {
                throw new BizException(Errors.NodeNameExists);
            }
        }
        dataNodeService.updateLocalDataNodeName(dataNodeUpdateReq.getNodeId(), dataNodeUpdateReq.getNodeName());
        return JsonResponse.success();
    }

}
