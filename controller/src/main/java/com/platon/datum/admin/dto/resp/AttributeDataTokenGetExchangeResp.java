package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.AttributeDataToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

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
public class AttributeDataTokenGetExchangeResp {


    /**
     * 交易所地址
     */
    private List<Exchange> exchangeList = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    @ApiModel
    public static class Exchange {

        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("地址")
        private String url;

        @ApiModelProperty("显示的图片")
        private String imageUrl;
    }
}
