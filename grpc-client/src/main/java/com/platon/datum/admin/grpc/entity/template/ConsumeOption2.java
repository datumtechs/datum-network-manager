package com.platon.datum.admin.grpc.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/26 12:29
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class ConsumeOption2 {

    private String contract;

    private Long cryptoAlgoConsumeUnit;

    private Long plainAlgoConsumeUnit;
}
