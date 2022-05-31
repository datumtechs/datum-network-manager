package com.platon.datum.admin.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author liushuyu
 * @Date 2022/4/28 18:12
 * @Version
 * @Desc
 */
public class LocalDateTimeUtil {


    /**
     * 将时间戳转换成UTC时间
     *
     * @param timestamp 时间戳
     * @return
     */
    public static LocalDateTime getLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
    }

    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTimeUtil.getLocalDateTime(1653552564775L);
        System.out.println(localDateTime);
    }
}
