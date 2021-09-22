package com.platon.metis.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import com.platon.metis.admin.grpc.channel.BaseChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.service.MetaDataRpcMessage;
import com.platon.metis.admin.grpc.service.MetadataServiceGrpc;
import com.platon.metis.admin.grpc.types.Metadata;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:29
 * @Version
 * @Desc 元数据服务客户端
 * java服务类：MetaDataServiceGrpc
 * proto文件：metadata_rpc_api.proto
 */

@Component
@Slf4j
public class MetaDataClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 上架或更新文件元数据
     * @param fileDetail 文件详情
     * @return 上架成功后的元数据ID
     */
    public String publishMetaData(LocalDataFileDetail fileDetail) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            //2.1文件所属组织者信息
            LocalOrg localOrgInfo = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
            CommonBase.Organization orgInfo = CommonBase.Organization
                    .newBuilder()
                    .setNodeName(localOrgInfo.getName())
                    .setNodeId(localOrgInfo.getCarrierNodeId())
                    .setIdentityId(localOrgInfo.getIdentityId())
                    .build();
            //2.2文件元数据信息
            Metadata.MetadataSummary.Builder summaryBuilder = Metadata.MetadataSummary
                    .newBuilder()
                    .setOriginId(fileDetail.getFileId())
                    .setTableName((String) fileDetail.getDynamicFields().get("resourceName"))
                    .setFilePath(fileDetail.getFilePath())
                    .setRows(fileDetail.getRows().intValue()) // 源文件的行数
                    .setColumns(fileDetail.getColumns())
                    .setSize(fileDetail.getSize().intValue())
                    .setFileType(CommonBase.OriginFileType.valueOf(fileDetail.getFileType()))
                    .setHasTitle(fileDetail.getHasTitle());
                    //.setState(fileDetail.getStatus());// 元数据的状态 (create: 还未发布的新表; release: 已发布的表; revoke: 已撤销的表)
           /* if(StrUtil.isNotBlank(fileDetail.getRemarks())){
                summaryBuilder.setDesc(fileDetail.getRemarks());
            }*/
            Metadata.MetadataDetail.Builder builder = Metadata.MetadataDetail
                    .newBuilder()
                    .setMetadataSummary(summaryBuilder.build());
            //2.3构建文件元数据列信息
            List<LocalMetaDataColumn> columnList = fileDetail.getLocalMetaDataColumnList();
            for (int i = 0; i < columnList.size(); i++) {
                LocalMetaDataColumn metaDataColumn = columnList.get(i);
                if(LocalMetaDataColumnVisibleEnum.NO.getIsVisible().equals(metaDataColumn.getVisible())){
                    //如果不可见则不发布该列元数据
                    continue;
                }
                Metadata.MetadataColumn.Builder columnDetailBuilder = Metadata.MetadataColumn.newBuilder();
                columnDetailBuilder.setCIndex(metaDataColumn.getColumnIdx());
                if (StrUtil.isNotBlank(metaDataColumn.getColumnName())){
                    columnDetailBuilder.setCName(metaDataColumn.getColumnName());
                }
                if (StrUtil.isNotBlank(metaDataColumn.getColumnType())){
                    columnDetailBuilder.setCType(metaDataColumn.getColumnType());
                }
                columnDetailBuilder.setCSize(metaDataColumn.getSize() == null ? 0 : metaDataColumn.getSize().intValue());
                if (StrUtil.isNotBlank(metaDataColumn.getRemarks())){
                    columnDetailBuilder.setCComment(metaDataColumn.getRemarks());
                }
                builder.addMetadataColumns(columnDetailBuilder.build());
            }
            //构建该元数据参与过得任务数 (已完成的和正在执行的)
            builder.setTotalTaskCount(0);//todo 统计该元数据参与过的任务

            //2.4收集完成文件信息
            Metadata.MetadataDetail metadataDetail = builder.build();
            //2.5构建完整的请求信息
            MetaDataRpcMessage.PublishMetadataRequest request = MetaDataRpcMessage.PublishMetadataRequest
                    .newBuilder()
                    .setInformation(metadataDetail)
                    .build();
            //3.调用rpc,获取response
            MetaDataRpcMessage.PublishMetadataResponse response = MetadataServiceGrpc.newBlockingStub(channel).publishMetadata(request);
            //4.处理response
            if (response.getStatus() != GRPC_SUCCESS_CODE) {
                throw new ApplicationException(StrUtil.format("元数据信息发布失败:{}",response.getMsg()));
            }
            return response.getMetadataId();
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 下架文件元数据
     * @param metaDataId 文件metaDataId
     * @return 上架成功后的元数据ID
     */
    public void revokeMetaData(String metaDataId) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            //2.1文件所属组织者信息
            /*LocalOrg localOrgInfo = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
            CommonMessage.OrganizationIdentityInfo orgInfo = CommonMessage.OrganizationIdentityInfo
                    .newBuilder()
                    .setName(localOrgInfo.getName())
                    .setNodeId(localOrgInfo.getCarrierNodeId())
                    .setIdentityId(localOrgInfo.getIdentityId())
                    .build();*/
            MetaDataRpcMessage.RevokeMetadataRequest request = MetaDataRpcMessage.RevokeMetadataRequest
                    .newBuilder()
                    .setMetadataId(metaDataId)
                    .build();
            //3.调用rpc,获取response
            CommonBase.SimpleResponse response = MetadataServiceGrpc.newBlockingStub(channel).revokeMetadata(request);
            //4.处理response
            if (response.getStatus() != GRPC_SUCCESS_CODE) {
                throw new ApplicationException(StrUtil.format("元数据信息下架失败:{}",response.getMsg()));
            }
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 查询全网数据列表
     * @return
     */
    public List<GlobalDataFileDetail> getMetaDataDetailList() throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            com.google.protobuf.Empty request = com.google.protobuf.Empty
                    .newBuilder()
                    .build();
            //3.调用rpc,获取response
            MetaDataRpcMessage.GetTotalMetadataDetailListResponse response = MetadataServiceGrpc.newBlockingStub(channel).getTotalMetadataDetailList(request);
            //4.处理response
            if (response.getStatus() != GRPC_SUCCESS_CODE) {
                throw new ApplicationException(StrUtil.format("查询全网数据列表失败:{}",response.getMsg()));
            }
            List<MetaDataRpcMessage.GetTotalMetadataDetailResponse> metaDataList = response.getMetadataListList();
            List<GlobalDataFileDetail> detailList = metaDataList.stream()
                    .map(metaData -> {
                        GlobalDataFileDetail detail = new GlobalDataFileDetail();
                        //元数据的拥有者
                        CommonBase.Organization owner = metaData.getOwner();
                        String identityId = owner.getIdentityId();
                        String orgName = owner.getNodeName();
                        detail.setIdentityId(identityId);
                        detail.setOrgName(orgName);
                        //元文件详情主体
                        Metadata.MetadataDetail information = metaData.getInformation();
                        //            string meta_data_id = 1;           // 元数据Id
                        //            string origin_id    = 2;              // 源文件Id
                        //            string table_name   = 3;             // 元数据名称 (表名)
                        //            string desc         = 4;                   // 元数据的描述 (摘要)
                        //            string file_path    = 5;              // 源文件存放路径
                        //            uint32 rows         = 6;                   // 源文件的行数
                        //            uint32 columns      = 7;                // 源文件的列数
                        //            uint32 size         = 8;                   // 源文件的大小 (单位: byte)
                        //            string file_type    = 9;              // 源文件的类型 (目前只有 csv)
                        //            bool   has_title    = 10;             // 源文件是否包含标题
                        //            string state        = 11;                 // 元数据的状态 (create: 还未发布的新表; release: 已发布的表; revoke: 已撤销的表)
                        Metadata.MetadataSummary summary = information.getMetadataSummary();
                        detail.setMetaDataId(summary.getMetadataId());
                        detail.setFileId(summary.getOriginId());
                        detail.setFileName(summary.getTableName());
                        detail.setResourceName(summary.getTableName());
                        detail.setRemarks(summary.getDesc());
                        detail.setFilePath(summary.getFilePath());
                        detail.setRows(Long.valueOf(summary.getRows()));
                        detail.setColumns(summary.getColumns());
                        detail.setSize(Long.valueOf(summary.getSize()));
                        detail.setFileType(summary.getFileType().name());
                        detail.setHasTitle(summary.getHasTitle());
                        detail.setStatus(LocalDataFileStatusEnum.RELEASED.getStatus());
                        //            uint32 cindex   = 1;                         // 列的索引
                        //            string cname    = 2;                          // 列名
                        //            string ctype    = 3;                          // 列类型
                        //            uint32 csize    = 4;                          // 列大小(单位: byte)
                        //            string ccomment = 5;                       // 列描述
                        information.getMetadataColumnsList().forEach(columnDetail -> {
                            GlobalMetaDataColumn metaDataColumn = new GlobalMetaDataColumn();
                            metaDataColumn.setFileId(summary.getOriginId());
                            metaDataColumn.setColumnIdx(columnDetail.getCIndex());
                            metaDataColumn.setColumnName(columnDetail.getCName());
                            metaDataColumn.setColumnType(columnDetail.getCType());
                            metaDataColumn.setSize(Long.valueOf(columnDetail.getCSize()));
                            metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                            metaDataColumn.setRemarks(columnDetail.getCComment());
                            detail.getMetaDataColumnList().add(metaDataColumn);
                        });
                        return detail;
                    })
                    .collect(Collectors.toList());
            return detailList;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

}
