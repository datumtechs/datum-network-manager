package com.platon.rosettanet.admin.common;

public class JsonResponse<T> {
    private int code = 0;   //0表示成功，其它表示错误代码
    private String msg;
    private int pageNo;       //	option	int	第几页数据
    private int totalPages;    //	option	int	数据总页数
    private int totalRows;     //	option	int	数据总条数
    private T data;


    public JsonResponse() {
    }

    public JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void setPagination(int pageNo, int pageSize, int totalRows ){
        //总记录数
        this.setTotalRows(totalRows);

        //总页数，每页记录数用请求消息里的定义
        int totalPages = (totalRows - 1) / pageSize + 1;

        //总页数，每页记录数用请求消息里的定义
        this.setTotalPages(totalPages);

        //当前数据是第几页，如果请求的页码小余重新计算的总记录数，则页码不变；否则页码就是总页数
        this.setPageNo(Math.min(pageNo, totalPages));
    }

    public static <T> JsonResponse<T> success(T data) {
        JsonResponse<T> response = new JsonResponse();
        response.code = 0;
        response.data = data;
        return response;
    }
}
