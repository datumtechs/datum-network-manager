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
public class Proposal {
    /**
    * 提案id
    */
    private String id;

    /**
    * 提案提交者
    */
    private String submitter;

    /**
    * 提案关联者
    */
    private String candidate;

    /**
    * 提案类型, 1-增加委员会成员; 2-剔除委员会成员; 3-委员会成员退出
    */
    private Integer type;

    /**
    * 提案的公示信息
    */
    private String publicityId;

    /**
    * 提交块高
    */
    private String submissionBn;

    /**
    * 投票开始块高
    */
    private String voteBeginBn;

    /**
    * 投票结束块高
    */
    private String voteEndBn;

    /**
    * 主动退出的块高
    */
    private String autoQuitBn;

    /**
    * 赞成票数量
    */
    private Integer voteAgreeNumber;

    /**
    * 委员总数，如果为空需要实时查询
    */
    private Integer authorityNumber;

    /**
    * 提案状态, 0-未开始; 1-投票中; 2-投票通过; 3-投票未通过; 4-退出中；5-已退出；6-已撤销
    */
    private Integer status;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;


    /*************************************************************************
    /**
     * 提交的组织id
     */
    private String submitterIdentityId;
    /**
     * 提交的组织名称
     */
    private String submitterNodeName;
    /**
     * 候选的组织id
     */
    private String candidateIdentityId;
    /**
     * 候选的组织名称
     */
    private String candidateNodeName;
    /**
     * 投票开始时间
     */
    private Date voteBeginTime;
    /**
     * 投票结束时间
     */
    private Date voteEndTime;
    /**
     * 提交时间
     */
    private Date submissionTime;
    /**
     * 提案公式信息
     */
    private Publicity publicity;
}