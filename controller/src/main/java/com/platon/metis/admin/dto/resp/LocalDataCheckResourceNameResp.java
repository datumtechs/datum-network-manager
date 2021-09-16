package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.constant.ControllerConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/16 14:38
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataCheckResourceNameResp {
    //是否可用，可用为Y，不可用为N
    @ApiModelProperty(name = "status", value = "是否可用，可用为Y，不可用为N")
    private String status = ControllerConstants.STATUS_NOT_AVAILABLE;
    @ApiModelProperty(name = "desc", value = "确保文件名称前12个字符不会和已存在的文件前12个字符重复，仅支持中英文与数字输入，最多12个字符")
    private String desc="确保文件名称前12个字符不会和已存在的文件前12个字符重复，仅支持中英文与数字输入，最多12个字符";
}
