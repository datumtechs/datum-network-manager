package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.VLocalStatsMapper;
import com.platon.metis.admin.dao.dto.UsedResourceDTO;
import com.platon.metis.admin.service.IndexService;
import com.platon.metis.admin.service.constant.ServiceConstant;
import com.platon.metis.admin.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:11
 * @Version
 * @Desc
 */

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private VLocalStatsMapper localStatsMapper;

    @Override
    public UsedResourceDTO queryUsedTotalResource() {
//        Map<String, Object> map = new HashMap<>(4);
        UsedResourceDTO usedResourceDTO  = localStatsMapper.queryUsedTotalResource();
//        if (null != usedResourceDTO) {
//            // 计算核数
//            BigDecimal usedCore = new BigDecimal(3 * 100)
//                    .divide(new BigDecimal(usedResourceDTO.getTotalCore()), 0, BigDecimal.ROUND_HALF_UP);
//            // 计算内存
//            BigDecimal usedMemory = new BigDecimal(usedResourceDTO.getUsedMemory() * 100)
//                    .divide(new BigDecimal(usedResourceDTO.getTotalMemory()), 0, BigDecimal.ROUND_HALF_UP)
//                    .divide(new BigDecimal(1024 * 1024 * 1024));
//            // 计算带宽
//            BigDecimal usedBandwidth = new BigDecimal(34444 * 100)
//                    .divide(new BigDecimal(usedResourceDTO.getTotalBandwidth()), 0, BigDecimal.ROUND_HALF_UP)
//                    .divide(new BigDecimal(1000 * 1000));
//
//            map.put("usedCore", usedCore + "%");
//            map.put("usedMemory", usedMemory + "%" );
//            map.put("usedBandwidth", usedBandwidth + "%" );
//        }
        return usedResourceDTO;
    }

    @Override
    public List<Long> queryPublishDataOrPower(String flag) {
        // 查询我发布的数据
        if (ServiceConstant.constant_1.equals(flag)) {
            return localStatsMapper.queryMyPublishData();
        }
        // 查询我发布的算力
        if (ServiceConstant.constant_2.equals(flag)) {
            return localStatsMapper.queryMyPublishPower();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> queryMyCalculateTaskStats() {
        return localStatsMapper.queryMyCalculateTaskStats();
    }

    @Override
    public List<Long> queryWholeNetDateOrPower(String flag) {
        // 查询全网数据走势
        if (ServiceConstant.constant_1.equals(flag)) {
           return localStatsMapper.queryWholeNetDateTrend();
        }
        // 查询全网算力走势
        if (ServiceConstant.constant_1.equals(flag)) {
            return localStatsMapper.queryWholeNetPowerTrend();
        }
        throw new ServiceException("请求标志错误，请稍后重试");
    }

    @Override
    public Map<String, Object> queryWholeNetDateTotalRatio() {
        Map<String, Object> map = new HashMap(4);
        // 查询全网数据总量月环比（上月/上上月）
        List<Double> monthList = localStatsMapper.queryWholeNetDateRingRatio();
        String ringRatio = this.calculateRatio(monthList);
        // 查询全网数据总量月同比（当前年上月/去年同上月）
        List<Double> sameList = localStatsMapper.queryWholeNetDateSameRatio();
        String sameRatio = this.calculateRatio(sameList);
        // 月环比
        map.put("ringRatio", ringRatio);
        // 月同比
        map.put("sameRatio", sameRatio);
        return map;
    }

    /** 计算环比 */
    private String calculateRatio(List<Double> list){
        // 上月数据
        BigDecimal pMonth = new BigDecimal(list.get(0));
        // 上上月数据
        BigDecimal ppMonth = new BigDecimal(list.get(1));
        // 上上月为0
        if (list.get(1) == 0) {
            return String.valueOf(list.get(0));
        }
        BigDecimal bd = pMonth.subtract(ppMonth).abs()
                .divide(ppMonth)
//                .multiply(new BigDecimal(100))
                .setScale(1, BigDecimal.ROUND_UP);
        return String.valueOf(bd);
    }

    @Override
    public List<Map<String, Object>> queryWaitAuthDataList() {
        return localStatsMapper.queryWaitAuthDataList();
    }

}
