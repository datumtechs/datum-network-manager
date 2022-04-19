package com.platon.metis.admin.dto;


import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.BizException;
import com.platon.metis.admin.common.exception.Errors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "返回参数封装类")
public class JsonResponse<T> {

    @ApiModelProperty(value = "0表示成功，非0标识异常。参考异常定义")
    private int status = 0;

    @ApiModelProperty(value = "返回内容提示")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "列表查询满足条件总条数（用于分页）")
    private int total = 0;

    @ApiModelProperty(value = "列表查询满足条件总页数")
    private int pageTotal = 0;

    @ApiModelProperty(value = "第几页数据")
    private int pageNumber = 1;

    @ApiModelProperty(value = "分页请求每页数据条数")
    private int pageSize = 0;

    public JsonResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResponse success() {
        return new JsonResponse(Errors.SUCCESS.getCode(), Errors.SUCCESS.getMessage(), null);
    }

    public static <T> JsonResponse success(T data) {
        return new JsonResponse(Errors.SUCCESS.getCode(), Errors.SUCCESS.getMessage(), data);
    }

    public static JsonResponse fail() {
        return new JsonResponse(Errors.SysException.getCode(), Errors.SysException.getMessage(), null);
    }

    public static JsonResponse fail(BizException e) {
        return new JsonResponse(e.getErrorCode(), e.getMessage(), null);
    }

    public static JsonResponse fail(Errors errors) {
        return new JsonResponse(errors.getCode(), errors.getMessage(), null);
    }


    /**
     * @param page 分页信息+列表内容
     * @param <T>
     * @return
     */
    public static <T> JsonResponse page(Page<T> page) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMsg(Errors.SUCCESS.getMessage());
        jsonResponse.setStatus(Errors.SUCCESS.getCode());
        jsonResponse.setPageNumber(page.getPageNum());
        jsonResponse.setPageSize(page.getPageSize());
        jsonResponse.setTotal((int) page.getTotal());
        jsonResponse.setPageTotal(page.getPages());
        jsonResponse.setData(page.getResult());
        return jsonResponse;
    }

    /**
     * @param pageInfo 分页信息
     * @param list     列表内容
     * @param <T>
     * @return
     */
    public static <T> JsonResponse page(Page pageInfo, List<T> list) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMsg(Errors.SUCCESS.getMessage());
        jsonResponse.setStatus(Errors.SUCCESS.getCode());
        jsonResponse.setPageNumber(pageInfo.getPageNum());
        jsonResponse.setPageSize(pageInfo.getPageSize());
        jsonResponse.setTotal((int) pageInfo.getTotal());
        jsonResponse.setPageTotal(pageInfo.getPages());
        jsonResponse.setData(list == null ? new ArrayList<>() : list);
        return jsonResponse;
    }
}