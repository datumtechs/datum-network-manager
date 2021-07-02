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
}
