package com.platon.datum.admin.dao.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author juzix
 * @Date 2022/8/10 17:32
 * @Version 
 * @Desc 
 *******************************
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
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}