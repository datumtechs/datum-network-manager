package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @Author liushuyu
 * @Date 2021/7/9 10:33
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class GlobalPowerPageResp {

    @ApiModelProperty(name = "identityId", value = "身份标识")
    private String identityId;//身份标识
    @ApiModelProperty(name = "orgName", value = "算力提供方名称")
    private String orgName;//算力提供方名称
    @ApiModelProperty(name = "status", value = "算力状态，0是空闲，1是已占用")
    private String status;//算力状态，0是空闲，1是已占用
    /**
     * 总CPU
     */
    @ApiModelProperty(name = "totalCore", value = "总CPU")
    private Integer totalCore;

    /**
     * 总内存
     */
    @ApiModelProperty(name = "totalMemory", value = "总内存")
    private Long totalMemory;

    /**
     * 总带宽
     */
    @ApiModelProperty(name = "totalBandwidth", value = "总带宽")
    private Long totalBandwidth;

    /**
     * 已使用CPU信息
     */
    @ApiModelProperty(name = "usedCore", value = "已使用CPU信息")
    private Integer usedCore;

    /**
     * 已使用内存
     */
    @ApiModelProperty(name = "usedMemory", value = "已使用内存")
    private Long usedMemory;

    /**
     * 已使用带宽
     */
    @ApiModelProperty(name = "usedBandwidth", value = "已使用带宽")
    private Long usedBandwidth;


    public static GlobalPowerPageResp from(GlobalPower globalPower){
        if(globalPower == null){
            return null;
        }
        GlobalPowerPageResp resp = new GlobalPowerPageResp();
        BeanUtils.copyProperties(globalPower,resp);
        if(globalPower.getUsedBandwidth() > 0
                || globalPower.getUsedCore() > 0
                || globalPower.getUsedMemory() > 0){
            resp.setStatus("1");
        } else {
            resp.setStatus("0");
        }
        return resp;
    }
}
