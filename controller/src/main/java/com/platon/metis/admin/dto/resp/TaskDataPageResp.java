package com.platon.metis.admin.dto.resp;

import com.platon.metis.admin.dao.BaseDomain;
import com.platon.metis.admin.dao.enums.RoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询计算任务列表返回实体")
public class TaskDataPageResp extends BaseDomain {

    @ApiModelProperty(name = "id",value = "序号")
    private Integer id;
    @ApiModelProperty(name = "taskId",value = "任务id")
    private String taskId;
    @ApiModelProperty(name = "taskName",value = "任务名称")
    private String taskName;
    @ApiModelProperty(name = "createAt",value = "任务发起时间 (时间戳),单位ms")
    private Long createAt;
    @ApiModelProperty(name = "startAt",value = "任务启动时间 (时间戳),单位ms")
    private Long startAt;
    @ApiModelProperty(name = "endAt",value = "任务结束时间 (时间戳),单位ms")
    private Long endAt;
    @ApiModelProperty(name = "status", value = "任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)")
    private Integer status;
    @ApiModelProperty(name = "reviewed", value = "是否查看过")
    private Boolean reviewed;


    /**
     *
     * @param role
     * @return
     */
    public static int getRole(String role){

        if(RoleEnum.OWNER.getMessage().equals(role)){
            return RoleEnum.OWNER.getCode();
        }else if(RoleEnum.DATASUPPLIER.getMessage().equals(role)){
            return RoleEnum.DATASUPPLIER.getCode();
        }else if(RoleEnum.POWERSUPPLIER.getMessage().equals(role)){
            return RoleEnum.POWERSUPPLIER.getCode();
        }else if(RoleEnum.RECEIVER.getMessage().equals(role)){
            return RoleEnum.RECEIVER.getCode();
        }else if(RoleEnum.ALGOSUPPLIER.getMessage().equals(role)){
            return RoleEnum.ALGOSUPPLIER.getCode();
        }else {
            return RoleEnum.UNDEFINED.getCode();
        }
    }


}
