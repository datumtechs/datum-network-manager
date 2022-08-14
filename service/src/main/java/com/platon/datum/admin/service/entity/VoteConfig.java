package com.platon.datum.admin.service.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class VoteConfig {
    /**
     * 从 发起提案 到 投票开始 需要经历的区块数
     */
    private BigInteger beginVote;
    /**
     * 从 投票开始 到 投票结束 需要经历的区块数
     */
    private BigInteger vote;
    /**
     * 从 发起主动退出提案 到 退出成功 需要经历的区块数
     */
    private BigInteger quit;
}
