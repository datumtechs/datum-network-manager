package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储结果实体类
 * @author houz
 */
@Data
public class StoreCalculateResult extends BaseDomain {

    /** 时间间隔 */
    private String timeInterval;

    /** 存储类型  */
    private String storeType;

    /** 所属时间 */
    private String resideTime;

    /** 计算结果 */
    private Long calculateResult;

    /** 是否有效（1:有效，2:无效） */
    private Byte status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

}