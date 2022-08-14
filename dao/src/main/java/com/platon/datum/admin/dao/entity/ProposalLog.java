package com.platon.datum.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version
 * @Desc ******************************
 */
@Getter
@Setter
@ToString
public class ProposalLog {
    /**
     * 日志表id(自增长)
     */
    private Long id;

    /**
     * 事件类型, 1-提交提案; 2-撤销提案; 3-对提案投票; 4-投票结果; 5-新增委员会; 6-删除委员会
     */
    private Integer type;

    /**
     * 事件所在块高
     */
    private String blockNumber;

    /**
     * 事件对应交易hash
     */
    private String txHash;

    /**
     * 事件日志index
     */
    private String logIndex;

    /**
     * 事件对应内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：0-未处理，1-已处理
     */
    private Integer status;

    @Getter
    public enum StatusEnum {

        TODO(0, "未处理"),
        DONE(1, "已处理"),
        ;

        private int value;
        private String desc;

        StatusEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

    }

    @Getter
    public enum TypeEnum {

        NEWPROPOSAL_EVENT(1, "提交提案"),
        WITHDRAWPROPOSAL_EVENT(2, "撤销提案"),
        VOTEPROPOSAL_EVENT(3, "对提案投票"),
        PROPOSALRESULT_EVENT(4, "投票结果"),
        AUTHORITYADD_EVENT(5, "新增委员会"),
        AUTHORITYDELETE_EVENT(6, "删除委员会");

        private int value;
        private String desc;

        TypeEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private static Map<Integer, TypeEnum> map = new HashMap<>();

        static {
            for (TypeEnum value : TypeEnum.values()) {
                map.put(value.getValue(), value);
            }
        }

        public static TypeEnum find(Integer value) {
            return map.get(value);
        }
    }
}