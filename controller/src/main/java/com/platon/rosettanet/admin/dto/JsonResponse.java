package com.platon.rosettanet.admin.dto;


import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JsonResponse<T> {
    private int status = 0;   //0表示成功，其它表示错误代码
    private String msg;
    private T data;

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

    public static JsonResponse fail(){
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage(),null);
    }

    public static JsonResponse fail(String message){
        return new JsonResponse(ResponseCodeEnum.FAIL.getCode(), message,null);
    }

    public static JsonResponse fail(ResponseCodeEnum code, String message){
        return new JsonResponse(code.getCode(),message,null);
    }

    public static JsonResponse fail(ResponseCodeEnum code){
        return new JsonResponse(code.getCode(),code.getMessage(),null);
    }

    public static <T> JsonResponse build(ResponseCodeEnum responseCodeEnum, String message, T data){
        return new JsonResponse(responseCodeEnum.getCode(),message,data);
    }
}
