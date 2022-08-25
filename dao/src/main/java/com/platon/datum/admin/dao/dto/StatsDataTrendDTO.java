package com.platon.datum.admin.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDataTrendDTO {
    /** 统计时间 */
    String statsTime;
    /** 每个月累加的 */
    Long totalValue;
    /** 当月的 */
    Long incrementValue;
}
