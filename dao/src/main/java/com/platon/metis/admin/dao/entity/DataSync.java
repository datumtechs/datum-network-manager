package com.platon.metis.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataSync {
    private String dataType;
    private LocalDateTime latestSynced;

    public static class DataType {
        public static String DataAuthReq = "data_auth_req";
        public static String LocalMetaData = "local_meta_data";
        public static String LocalTask = "local_task";
    }
}
