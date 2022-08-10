package com.platon.datum.admin.dto.resp;


import com.platon.datum.admin.dao.entity.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Data
@ApiModel(value = "用户登录响应")
public class LoginResp {
    @ApiModelProperty(value = "用户名", example = "")
    private String userName;
    @ApiModelProperty(value = "钱包地址", example = "")
    private String address;
    @ApiModelProperty(value = "用户状态：0-无效，1-有效", example = "")
    private Integer status;
    @ApiModelProperty(value = "是否是管理员：0-否，1-是", example = "")
    private Integer isAdmin;
    @ApiModelProperty(value = "资源列表")
    private List<Resource> resourceList;

    @ApiModelProperty(value = "组织信息完成度(0:待申请组织身份ID; 1:待完善组织头像和描述信息; 2：已完成基本信息)", notes = "")
    private Integer orgInfoCompletionLevel;

    @ApiModelProperty(value = "组织是否加入了网络(0未入网，1已入网， 99已退网)", notes = "")
    private Integer connectNetworkStatus;

    @Getter
    public enum CompletionLevel {
        SET_NAME(1, "待设置名称"),
        SET_DID(2, "待设置did"),
        SET_DESC(3, "待设置描述"),
        SET_NET(4, "待设置网络"),;
        private Integer level;
        private String desc;

        CompletionLevel(Integer level, String desc) {
            this.level = level;
            this.desc = desc;
        }

        public String toString() {
            return level + "-" + desc;
        }
    }

}
