package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.entity.DataToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/28 18:10
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenGetPublishConfigResp {


    @ApiModelProperty("合约工厂地址")
    private String dataTokenFactory;

    @ApiModelProperty("元数据对应的的dataToken信息")
    private DataToken dataToken;
}
