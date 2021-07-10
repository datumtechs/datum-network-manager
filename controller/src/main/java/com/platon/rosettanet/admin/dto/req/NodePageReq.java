package com.platon.rosettanet.admin.dto.req;

import javax.validation.constraints.NotNull;

/**
 * @author lyf
 * @Description 节点分页查询请求类
 * @date 2021/7/8 17:14
 */
public class NodePageReq {
    /**
     * 起始页号
     */
    @NotNull(message = "起始页号不能为空")
    private Integer pageNumber;

    /**
     * 每页数据条数
     */
    @NotNull(message = "每页数据条数不能为空")
    private Integer pageSize;

    /**
     * 搜索关键字
     */
    private String keyword;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
