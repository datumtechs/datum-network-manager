package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.AttributeDataToken;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/11 18:25
 * @Version
 * @Desc
 */

@ToString
@Getter
@Setter
@ApiModel
public class AttributeDataTokenGetPublishConfigResp {


    /**
     * 合约工厂地址
     */
    private String factoryAddress;

    private AttributeDataToken dataToken;
}
