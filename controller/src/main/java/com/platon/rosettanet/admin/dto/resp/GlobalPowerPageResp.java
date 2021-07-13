package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.GlobalDataFile;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;
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
public class GlobalPowerPageResp {

    private String identityId;//身份标识
    private String orgName;//算力提供方名称
    private String status;//算力状态，0是空闲，1是已占用
    /**
     * 总CPU
     */
    private Integer totalCore;

    /**
     * 总内存
     */
    private Long totalMemory;

    /**
     * 总带宽
     */
    private Long totalBandwidth;

    /**
     * 已使用CPU信息
     */
    private Integer usedCore;

    /**
     * 已使用内存
     */
    private Long usedMemory;

    /**
     * 已使用带宽
     */
    private Long usedBandwidth;


    public static GlobalPowerPageResp from(GlobalPower globalPower){
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
