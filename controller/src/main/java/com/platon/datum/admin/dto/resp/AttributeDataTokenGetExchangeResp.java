package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.AttributeDataToken;
import io.swagger.annotations.ApiModel;
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
     * 合约工厂地址
     */
    private List<Pair<String,String>> exchangeList = new ArrayList<>();
}
