package com.platon.metis.admin.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lyf
 * @Description 数据节点修改请求类
 * @date 2021/7/9 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsedResourceDTO {

    /** 使用核数 */
    private Integer usedCore;

    /** 使用内存 */
    private Long usedMemory;

    /** 使用带宽 */
    private Long usedBandwidth;

    /** 总内存 */
    private Long totalMemory;

    /** 总核数 */
    private Integer totalCore;

    /** 总带宽 */
    private Long totalBandwidth;

}
