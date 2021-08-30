package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/15 11:57
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataUpdateReq {

    /**
     * 序号
     */
    @ApiModelProperty(value = "metaData数据ID",required = true)
    @NotNull
    private Integer id;
    //数据描述
    @ApiModelProperty(value = "数据描述",required = true)
    private String remarks;
    //行业
    @ApiModelProperty(value = "所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业",required = true)
    private Integer industry;
    //源文件列信息
    @ApiModelProperty(value = "源文件列信息",required = true)
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
}
