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
    /** 累计值 */
    Long totalValue;
    /** 增量值 */
    Long incrementValue;
}
