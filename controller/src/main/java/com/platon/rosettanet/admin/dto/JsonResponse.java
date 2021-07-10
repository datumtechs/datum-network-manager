package com.platon.rosettanet.admin.dto;


import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class JsonResponse<T> {
    private int status = 0;   //0表示成功，其它表示错误代码
    private String msg;
    private T data;
    private int total=0;          //列表查询满足条件总条数（用于分页）
    private int pageTotal=0;      //	option	int	数据总页数
    private int pageNumber=1;     //	option	int	第几页数据
    private int pageSize=0;       //	option	int	分页请求每页数据条数
    private List<T> list;       //返回报文JSON数组对象[{},{}...]

    public JsonResponse(){}

    public JsonResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(int status, String msg, T data, List<T> list) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.list = list;
    }

    public static JsonResponse success(){
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(),null);
    }

    public static <T> JsonResponse success(T data){
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(),data);
    }

    public static <T> JsonResponse success(T data, List<T> list){
        return new JsonResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(),data,list);
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
        jsonResponse.setList(page.getResult() == null ? new ArrayList() : page.getResult());
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
        jsonResponse.setList(list == null ? new ArrayList() : list);
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
