package com.platon.datum.admin.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsPowerTrendDTO {
    /** 统计时间 */
    String statsTime;
    /** 累计值 */
    Long totalMemoryValue;
    /** 增量值 */
    Long incrementMemoryValue;

    /** 累计值 */
    Long totalCoreValue;
    /** 增量值 */
    Long incrementCoreValue;

    /** 累计值 */
    Long totalBandwidthValue;
    /** 增量值 */
    Long incrementBandwidthValue;
}
