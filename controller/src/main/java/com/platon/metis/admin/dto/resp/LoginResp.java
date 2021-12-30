package com.platon.metis.admin.dto.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;


@Data
@ApiModel(value = "用户登录响应")
public class LoginResp {
    @ApiModelProperty(value = "用户id", example = "")
    private String userId;

    @ApiModelProperty(value = "组织信息完成度(0:待申请组织身份ID; 1:待完善组织头像和描述信息; 2:待接入网络; 3:已入网)", notes="")
    private Integer orgInfoCompletionLevel;

    @Getter
    public enum CompletionLevel{
        NEED_IDENTITY_ID(0, "待申请组织身份ID"),
        NEED_PROFILE(1, "待完善组织头像和描述信息"),
        NEED_CONNECT_NET(2, "待接入网络"),
        CONNECTED(3, "已入网");

        private final Integer level;
        private final String desc;

        CompletionLevel(Integer level, String desc) {
            this.level = level;
            this.desc = desc;
        }

        public String toString() {
            return level + "-" + desc;
        }
    }

    public static void main(String args[]){
        System.out.println(CompletionLevel.CONNECTED.getLevel());
    }
}
