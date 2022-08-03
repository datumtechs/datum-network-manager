package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.DataFile;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.dao.entity.MetaDataColumn;
import com.platon.datum.admin.dao.enums.DataFileStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
public class MetaDataDetailResp {

    /**
     * 序号
     */
    @ApiModelProperty(name = "id", value = "localMetaData数据库id")
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
    private Integer rows;
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
    //参与任务数量
    @ApiModelProperty(name = "attendTaskCount", value = "参与任务数量")
    private Integer attendTaskCount;
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
    @ApiModelProperty(name = "metaDataColumnList", value = "源文件列信息")
    private List<MetaDataColumn> metaDataColumnList = new ArrayList<>();

    //拥有者钱包地址
    @ApiModelProperty(name = "user", value = "拥有者钱包地址")
    private String user;
    @ApiModelProperty(name = "userType", value = "用户类型：1-eth，2-alaya，3-platon")
    private Integer userType;
    //表示该元数据是 普通数据 还是 模型数据的元数据 (0: 未定义; 1: 普通数据元数据; 2: 模型数据元数据)
    @ApiModelProperty(name = "metaDataType", value = "表示该元数据是 普通数据 还是 模型数据的元数据 (0: 未定义; 1: 普通数据元数据; 2: 模型数据元数据)")
    private Integer metaDataType;

    @ApiModelProperty(name = "usage", value = "用法：1-明文，2-密文，3-都支持")
    private Integer usage;

    @ApiModelProperty(name = "dataTokenAddress", value = "绑定后的无属性数据凭证地址，执行绑定动作前该字段都是空的")
    private String dataTokenAddress;

    @ApiModelProperty(name = "attributeDataTokenAddress", value = "绑定后的有属性数据凭证地址，执行绑定动作前该字段都是空的")
    private String attributeDataTokenAddress;
    //v0.4.0源数据的存储位置类型 (组织本地服务器、远端服务器、云等)
    private Integer locationType;

    public static MetaDataDetailResp from(DataFile dataFile, MetaData metaData) {
        if (dataFile == null) {
            return null;
        }
        MetaDataDetailResp resp = new MetaDataDetailResp();
        BeanUtils.copyProperties(dataFile, resp);

        resp.setMetaDataColumnList(metaData.getMetaDataColumnList());

        int status = metaData.getStatus();
        resp.setRemarks(metaData.getDesc());
        resp.setIndustry(metaData.getIndustry());
        resp.setMetaDataId(metaData.getMetaDataId());

        resp.setAttendTaskCount((Integer) metaData.getField("taskCount"));
        resp.setResourceName(metaData.getMetaDataName());
        resp.setId(metaData.getId());
        //元数据状态:1已发布，0未发布
        if (DataFileStatusEnum.RELEASED.getStatus() == status) {
            resp.setStatus("1");
        } else {
            resp.setStatus("0");
        }
        resp.setRecCreateTime(metaData.getRecCreateTime() == null ? null : LocalDateTimeUtil.getTimestamp(metaData.getRecCreateTime()));
        resp.setRecUpdateTime(metaData.getRecUpdateTime() == null ? null : LocalDateTimeUtil.getTimestamp(metaData.getRecUpdateTime()));
        resp.setUser(metaData.getUser());
        resp.setUserType(metaData.getUserType());
        resp.setMetaDataType(metaData.getMetaDataType());
        resp.setUsage(metaData.getUsage());
        resp.setDataTokenAddress(metaData.getDataTokenAddress());
        resp.setAttributeDataTokenAddress(metaData.getAttributeDataTokenAddress());
        resp.setLocationType(dataFile.getLocationType());
        return resp;
    }
}
