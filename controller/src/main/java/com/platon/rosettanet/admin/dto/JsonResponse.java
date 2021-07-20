package com.platon.rosettanet.admin.dto;


import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
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

    @ApiModelProperty(value = "0表示成功，500表示系统内部异常，1000表示用户未登录，1001表示未申请身份标识，1002表示无可用的调度服务")
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
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> JsonResponse success(T data) {
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> JsonResponse success(ResponseCodeEnum responseCodeEnum, String message, T data) {
        return new JsonResponse(responseCodeEnum.getCode(), message, data);
    }

    public static JsonResponse fail() {
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage(), null);
    }

    public static JsonResponse fail(String message) {
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), message, null);
    }

    public static JsonResponse fail(ResponseCodeEnum code) {
        return new JsonResponse(code.getCode(), code.getMessage(), null);
    }

    public static JsonResponse fail(ResponseCodeEnum code, String message) {
        return new JsonResponse(code.getCode(), message, null);
    }

    /**
     * @param page 分页信息+列表内容
     * @param <T>
     * @return
     */
    public static <T> JsonResponse page(Page<T> page) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        jsonResponse.setStatus(ResponseCodeEnum.SUCCESS.getCode());
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
        jsonResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        jsonResponse.setStatus(ResponseCodeEnum.SUCCESS.getCode());
        jsonResponse.setPageNumber(pageInfo.getPageNum());
        jsonResponse.setPageSize(pageInfo.getPageSize());
        jsonResponse.setTotal((int) pageInfo.getTotal());
        jsonResponse.setPageTotal(pageInfo.getPages());
        jsonResponse.setData(list == null ? new ArrayList<>() : list);
        return jsonResponse;
    }
}
