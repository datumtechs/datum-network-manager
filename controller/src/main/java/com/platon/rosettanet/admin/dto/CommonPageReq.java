package com.platon.rosettanet.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:20
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class CommonPageReq {

    @NotNull
    private Integer pageNumber;//起始页号
    @NotNull
    private Integer pageSize;//每页数据条数
}
