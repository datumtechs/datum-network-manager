package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.rosettanet.admin.service.constant.ServiceConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2021/7/15 12:02
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataDetailResp {

    /**
     * 序号
     */
    @ApiModelProperty(name = "id", value = "序号")
    private Integer id;
    //组织身份ID
    @ApiModelProperty(name = "identityId", value = "组织身份ID")
    private String identityId;
    //源文件ID，上传文件成功后返回源文件ID
    @ApiModelProperty(name = "fileId", value = "源文件ID，上传文件成功后返回源文件ID")
    private String fileId;
    //文件名称
    @ApiModelProperty(name = "fileName", value = "文件名称")
    private String fileName;
    //文件存储路径
    @ApiModelProperty(name = "filePath", value = "文件存储路径")
    private String filePath;
    //文件后缀/类型, csv
    @ApiModelProperty(name = "fileType", value = "文件后缀/类型, csv")
    private String fileType;
    //资源名称
    @ApiModelProperty(name = "resourceName", value = "资源名称")
    private String resourceName;
    //文件大小(字节)
    @ApiModelProperty(name = "size", value = "文件大小(字节)")
    private Long size;
    //数据行数(不算title)
    @ApiModelProperty(name = "rows", value = "数据行数(不算title)")
    private Long rows;
    //数据列数
    @ApiModelProperty(name = "columns", value = "数据列数")
    private Integer columns;
    //是否带标题
    @ApiModelProperty(name = "hasTitle", value = "是否带标题")
    private Boolean hasTitle;
    //数据描述
    @ApiModelProperty(name = "remarks", value = "数据描述")
    private String remarks;
    //所属行业
    @ApiModelProperty(name = "industry", value = "所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业")
    private Integer industry;
    //元数据状态:1已发布，0未发布
    @ApiModelProperty(name = "status", value = "元数据状态:1已发布，0未发布")
    private String status;
    //元数据ID,hash
    @ApiModelProperty(name = "metaDataId", value = "元数据ID,hash")
    private String metaDataId;
    //创建时间
    @ApiModelProperty(name = "recCreateTime", value = "创建时间,单位ms")
    private Long recCreateTime;
    //最后更新时间
    @ApiModelProperty(name = "recUpdateTime", value = "最后更新时间,单位ms")
    private Long recUpdateTime;

    //源文件列信息
    @ApiModelProperty(name = "localMetaDataColumnList", value = "源文件列信息")
    private List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();

    public static LocalDataDetailResp from(LocalDataFileDetail detail){
        if(detail == null){
            return null;
        }
        LocalDataDetailResp resp = new LocalDataDetailResp();
        BeanUtils.copyProperties(detail,resp);
        Map dynamicFields = detail.getDynamicFields();
        String status = (String) dynamicFields.get(ServiceConstant.LOCAL_DATA_STATUS);
        String metaDataId = (String) dynamicFields.get(ServiceConstant.LOCAL_DATA_METADATA_ID);
        String remarks = (String) dynamicFields.get(ServiceConstant.LOCAL_DATA_REMARKS);
        Integer industry = (Integer) dynamicFields.get(ServiceConstant.LOCAL_DATA_INDUSTRY);
        resp.setRemarks(remarks);
        resp.setIndustry(industry);
        resp.setMetaDataId(metaDataId);
        //元数据状态:1已发布，0未发布
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(status)){
            resp.setStatus("1");
        } else {
            resp.setStatus("0");
        }
        resp.setRecCreateTime(detail.getRecCreateTime() == null? null : detail.getRecCreateTime().getTime());
        resp.setRecUpdateTime(detail.getRecUpdateTime() == null? null : detail.getRecUpdateTime().getTime());
        return resp;
    }
}
