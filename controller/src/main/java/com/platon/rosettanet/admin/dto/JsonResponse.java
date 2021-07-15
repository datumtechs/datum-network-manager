package com.platon.rosettanet.admin.dto;


import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author
 * @param <T>
 */
@Data
@ApiModel(value = "返回参数封装类")
public class JsonResponse<T> {

    @ApiModelProperty(value = "0表示成功，其它表示错误代码")
    private int status = 0;

    @ApiModelProperty(value = "返回内容提示")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "列表查询满足条件总条数（用于分页）")
    private int total=0;

    @ApiModelProperty(value = "列表查询满足条件总页数")
    private int pageTotal=0;

    @ApiModelProperty(value = "第几页数据")
    private int pageNumber=1;

    @ApiModelProperty(value = "分页请求每页数据条数")
    private int pageSize=0;

    public JsonResponse(){}

    public JsonResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResponse success(){
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(),null);
    }

    public static <T> JsonResponse success(T data){
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(),data);
    }

    public static <T> JsonResponse success(ResponseCodeEnum responseCodeEnum, String message, T data){
        return new JsonResponse(responseCodeEnum.getCode(),message,data);
    }

    public static JsonResponse fail(){
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage(),null);
    }

    public static JsonResponse fail(String message){
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), message,null);
    }

    public static JsonResponse fail(ResponseCodeEnum code){
        return new JsonResponse(code.getCode(),code.getMessage(),null);
    }

    public static JsonResponse fail(ResponseCodeEnum code, String message){
        return new JsonResponse(code.getCode(),message,null);
    }

    /**
     *
     * @param page 分页信息+列表内容
     * @param <T>
     * @return
     */
    public static <T> JsonResponse page(Page<T> page){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        jsonResponse.setStatus(ResponseCodeEnum.SUCCESS.getCode());
        jsonResponse.setPageNumber(page.getPageNum());
        jsonResponse.setPageSize(page.getPageSize());
        jsonResponse.setTotal((int)page.getTotal());
        jsonResponse.setPageTotal(page.getPages());
        return jsonResponse;
    }

    /**
     *
     * @param pageInfo 分页信息
     * @param list 列表内容
     * @param <T>
     * @return
     */
    public static <T> JsonResponse page(Page pageInfo,List<T> list){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMsg(ResponseCodeEnum.SUCCESS.getMessage());
        jsonResponse.setStatus(ResponseCodeEnum.SUCCESS.getCode());
        jsonResponse.setPageNumber(pageInfo.getPageNum());
        jsonResponse.setPageSize(pageInfo.getPageSize());
        jsonResponse.setTotal((int)pageInfo.getTotal());
        jsonResponse.setPageTotal(pageInfo.getPages());
        return jsonResponse;
    }

    public void setPagination(int pageNo, int pageSize, int totalRows ){
        //总记录数
        this.total = totalRows;

        //总页数，每页记录数用请求消息里的定义
        int totalPages = (totalRows - 1) / pageSize + 1;

        //总页数，每页记录数用请求消息里的定义
        this.pageTotal = totalPages;

        //当前数据是第几页，如果请求的页码小余重新计算的总记录数，则页码不变；否则页码就是总页数
        this.pageNumber = Math.min(pageNo, pageTotal);
    }
}
