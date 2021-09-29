package com.platon.metis.admin.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author houz
 * 计算节点实体类
 */
@Data
public class LocalPowerNode {

    private Integer id;

    private String identityId;

    private String powerNodeId;

    private String powerNodeName;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

    private LocalDateTime startTime;

    private String remarks;

    private String connMessage;

    private String powerId;

    private Integer powerStatus;

    private LocalDateTime connTime;

    private Integer connStatus;

    public static enum ConnStatus {
        disconnected(0, "disconnected"),connected(1, "connected");
        private int code;
        private String value;

        ConnStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static ConnStatus codeOf(int code) {
            for (ConnStatus status : ConnStatus.values()) {
                if (status.ordinal()== code) {
                    return status;
                }
            }
            return null;
        }

    }
    private Long memory;

    private Integer core;

    private Long bandwidth;

    private Long usedMemory;

    private Integer usedCore;

    private Long usedBandwidth;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}