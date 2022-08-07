package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dao.entity.MetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/15 12:00
 * @Version
 * @Desc
 */

@Data
@ApiModel
public class AddLocalMetaDataReq {

    //数据库id
    @ApiModelProperty(value = "fileId, 数据文件全网唯一ID，文件上传成功后由数据节点产生", required = true)
    @NotBlank
    private String fileId;
    @ApiModelProperty(value = "数据添加类型 1：新增数据、2：另存为新数据", required = true)
    private Integer addType;
    //资源名称
    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank
    private String resourceName;
    //数据描述
    @ApiModelProperty(value = "数据描述", required = true)
    @NotBlank
    private String desc;
    //行业
    @ApiModelProperty(value = "所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业", required = true)
    @NotNull
    private Integer industry;
    //源文件列信息
    @ApiModelProperty(value = "源文件列信息", required = true)
    private List<MetaDataColumn> metaDataColumnList = new ArrayList<>();


    /**
     * @since v0.5.0
     */
    @ApiModelProperty(name = "usage", value = "用法：1-明文，2-密文，3-都支持")
    @NotNull
    private Integer usage;
}
