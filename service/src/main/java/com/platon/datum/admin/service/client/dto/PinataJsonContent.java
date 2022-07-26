package com.platon.datum.admin.service.client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/25 12:10
 * @Version
 * @Desc
 */



@Getter
@Setter
@ToString
public class PinataJsonContent{
    private String image;

    private String desc;

    private String remark;
}
