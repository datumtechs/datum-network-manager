package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
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
@ApiModel
public class Proposal extends BaseDomain implements Business {

    /**
     * 提案id
     */
    @ApiModelProperty("提案id")
    private String id;

    /**
     * 提案提交者
     */
    @ApiModelProperty("提案提交者")
    private String submitter;

    /**
     * 提案关联者
     */
    @ApiModelProperty("提案关联者")
    private String candidate;

    /**
     * 提案类型, 1-增加委员会成员; 2-剔除委员会成员; 3-委员会成员退出
     */
    @ApiModelProperty("提案类型, 1-增加委员会成员; 2-剔除委员会成员; 3-委员会成员退出")
    private Integer type;

    /**
     * 提交块高
     */
    @ApiModelProperty("提交块高")
    private String submissionBn;

    /**
     * 投票开始块高
     */
    @ApiModelProperty("投票开始块高")
    private String voteBeginBn;

    /**
     * 投票结束块高
     */
    @ApiModelProperty("投票结束块高")
    private String voteEndBn;

    /**
     * 主动退出成功的块高
     */
    @ApiModelProperty("主动退出成功的块高")
    private String autoQuitBn;

    /**
     * 赞成票数量
     */
    @ApiModelProperty("赞成票数量")
    private Integer voteAgreeNumber;

    /**
     * 委员总数，如果为空(即在投票中的状态)需要实时查询
     */
    @ApiModelProperty("委员总数，如果为空(即在投票中的状态)需要实时查询")
    private Integer authorityNumber;

    /**
     * 提案状态：0-投票未开始；1-投票开始；2-投票结束，但是还未通过；3-投票通过；4-投票未通过；5-退出中；6-已退出；7-撤销中；8-已撤销
     */
    @ApiModelProperty("提案状态：0-投票未开始；1-投票开始；2-投票结束，但是还未通过；3-投票通过；4-投票未通过；5-退出中；6-已退出；7-撤销中；8-已撤销")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


    /****************************************公示信息***********************************/
    /**
     * 提案附言
     */
    @ApiModelProperty("提案附言")
    private String remark;

    /**
     * 提案的材料
     */
    @ApiModelProperty("提案的材料")
    private String material;

    /**
     * 提案材料的描述
     */
    @ApiModelProperty("提案材料的描述")
    private String materialDesc;

    @Getter
    public enum StatusEnum {

        HAS_NOT_STARTED(0, "投票未开始"),
        VOTE_START(1, "投票开始"),

        VOTE_END(2, "投票结束，但是还未通过"),
        VOTE_PASS(3, "投票通过"),
        VOTE_NOT_PASS(4, "投票未通过"),
        EXITING(5, "退出中"),
        SIGNED_OUT(6, "已退出"),
        REVOKING(7, "撤销中"),
        REVOKED(8, "已撤销"),
        ;

        private int status;
        private String desc;

        StatusEnum(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private static Map<Integer, StatusEnum> map = new HashMap<>();

        static {
            for (StatusEnum value : StatusEnum.values()) {
                map.put(value.getStatus(), value);
            }
        }

        public static StatusEnum find(Integer value) {
            return map.get(value);
        }

        public static StatusEnum find(BigInteger value) {
            return find(value.intValue());
        }
    }

    @Getter
    public enum TypeEnum {

        ADD_AUTHORITY(1, "提名加入委员会"),
        KICK_OUT_AUTHORITY(2, "提名退出委员会"),
        AUTO_QUIT_AUTHORITY(3, "主动退出委员会"),
        ;

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

        public static TypeEnum find(BigInteger value) {
            return find(value.intValue());
        }
    }
}