package com.platon.rosettanet.admin.dto.req;

/**
 * @author lyf
 * @Description 节点名称校验请求类
 * @date 2021/7/9 11:09
 */
public class CheckDataNodeNameReq {
    /**
     * 节点名称
     */
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
