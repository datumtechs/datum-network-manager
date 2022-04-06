package com.platon.metis.admin.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @Author liushuyu
 * @Date 2022/3/16 4:56
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel("资源")
public class Resource {

    //资源ID
    @ApiModelProperty(value = "资源ID")
    private Integer id;

    //资源类型：1-接口url，2-导航栏菜单，3-页面按钮
    @ApiModelProperty(value = "资源类型：1-接口url，2-导航栏菜单，3-页面按钮")
    private Integer type;

    //资源名称
    @ApiModelProperty(value = "资源名称")
    private String name;

    //资源内容：根据type改变，如果是接口url，则value为url，如果type是其他的，则是前端定义值
    @ApiModelProperty(value = "资源内容：根据type改变，如果是接口url，则value为url，如果type是其他的，则是前端定义值")
    private String value;

    //按钮或者菜单对应的url的资源id
    @ApiModelProperty(value = "按钮或者菜单对应的url的资源id")
    private Integer urlResourceId;

    //父资源ID,如果没有父资源ID，则设置0
    @ApiModelProperty(value = "父资源ID,如果没有父资源ID，则设置0")
    private Integer parentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
