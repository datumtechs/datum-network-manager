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
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
@Getter
@Setter
@ToString
@ApiModel
public class AuthorityBusiness extends BaseDomain {

    @ApiModelProperty("id")
    private Integer id;

    /**
     * 业务类型：1-申请认证，101-提名加入提案，102-提名踢出提案
     */
    @ApiModelProperty("业务类型：1-申请认证，101-提名加入提案，102-提名踢出提案")
    private Integer type;

    /**
     * 关联的提案表和vc申请表ID
     */
    @ApiModelProperty("关联的提案表和vc申请表ID")
    private String relationId;

    /**
     * 发起申请的组织
     */
    @ApiModelProperty("发起申请的组织")
    private String applyOrg;

    /**
     * 提案中涉及到的组织或者申请认证指定的审批组织
     */
    @ApiModelProperty("提案中涉及到的组织或者申请认证指定的审批组织")
    private String specifyOrg;

    /**
     * 业务开始时间
     */
    @ApiModelProperty("业务开始时间")
    private LocalDateTime startTime;

    /**
     * 处理状态：0-未处理，1-同意，2-不同意
     */
    @ApiModelProperty("处理状态：0-未处理，1-同意，2-不同意")
    private Integer processStatus;


    /**
     * 业务类型：1-申请认证，101-提名加入提案，102-提名踢出提案
     */
    @Getter
    @ToString
    public enum TypeEnum {
        APPLY_VC(1, "申请认证"),
        JOIN_PROPOSAL(101, "提名加入提案"),
        KICK_PROPOSAL(102, "提名踢出提案"),
        ;

        TypeEnum(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        private int type;
        private String desc;

        private static Map<Integer, TypeEnum> map = new HashMap<>();

        static {
            for (TypeEnum value : TypeEnum.values()) {
                map.put(value.getType(), value);
            }
        }

        public static TypeEnum find(Integer value) {
            return map.get(value);
        }

        public static TypeEnum find(BigInteger value) {
            return find(value.intValue());
        }
    }

    /**
     * 处理状态：0-未处理，1-同意，2-不同意
     */
    @Getter
    @ToString
    public enum ProcessStatusEnum {
        TO_DO(0, "未处理"),
        AGREE(1, "同意"),
        DISAGREE(2, "不同意"),
        ;

        ProcessStatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;

        private static Map<Integer, ProcessStatusEnum> map = new HashMap<>();

        static {
            for (ProcessStatusEnum value : ProcessStatusEnum.values()) {
                map.put(value.getStatus(), value);
            }
        }

        public static ProcessStatusEnum find(Integer value) {
            return map.get(value);
        }

        public static ProcessStatusEnum find(BigInteger value) {
            return find(value.intValue());
        }
    }
}