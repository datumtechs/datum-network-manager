package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.Empty;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.dao.enums.FileTypeEnum;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.rosettanet.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.service.*;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.platon.rosettanet.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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
    public String publishMetaData(LocalDataFileDetail fileDetail, LocalMetaData localMetaData) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            //2.1文件元数据摘要
            OriginFileType fileType = OriginFileType.forNumber(fileDetail.getFileType() == 0 ? FileTypeEnum.FILETYPE_UNKONW.getValue() : FileTypeEnum.FILETYPE_CSV.getValue());
            MetadataSummary metadataSummary = MetadataSummary.newBuilder()
                                                    .setMetadataId(localMetaData.getMetaDataId())
                                                    .setOriginId(fileDetail.getFileId())
                                                    .setTableName(fileDetail.getFileName())
                                                    .setDesc(localMetaData.getRemarks())
                                                    .setFilePath(fileDetail.getFilePath())
                                                    .setRows(fileDetail.getRows())
                                                    .setColumns(fileDetail.getColumns())
                                                    .setSize(fileDetail.getSize())
                                                    .setFileType(fileType)
                                                    .setHasTitle(fileDetail.getHasTitle())
                                                    .setIndustry(String.valueOf(localMetaData.getIndustry()))
                                                    .setState(MetadataState.valueOf(localMetaData.getStatus()))
                                                    .build();
            //2.2文件元数据对应原始文件对外暴露的列描述列表
            List<MetadataColumn> metadataColumnList = new ArrayList<>();
            List<LocalMetaDataColumn> columnList = fileDetail.getLocalMetaDataColumnList();
            for (int i = 0; i < columnList.size(); i++) {
                LocalMetaDataColumn metaDataColumn = columnList.get(i);
                if(LocalMetaDataColumnVisibleEnum.NO.getIsVisible().equals(metaDataColumn.getVisible())){
                    //如果不可见则不发布该列元数据
                    continue;
                }
                MetadataColumn.Builder metadataColumnBuilder = MetadataColumn.newBuilder();
                metadataColumnBuilder.setCIndex(metaDataColumn.getColumnIdx());
                if (StrUtil.isNotBlank(metaDataColumn.getColumnName())){
                    metadataColumnBuilder.setCName(metaDataColumn.getColumnName());
                }
                if (StrUtil.isNotBlank(metaDataColumn.getColumnType())){
                    metadataColumnBuilder.setCType(metaDataColumn.getColumnType());
                }
                metadataColumnBuilder.setCSize(metaDataColumn.getSize() == null ? 0 : metaDataColumn.getSize().intValue());
                if (StrUtil.isNotBlank(metaDataColumn.getRemarks())){
                    metadataColumnBuilder.setCComment(metaDataColumn.getRemarks());
                }
                metadataColumnList.add(metadataColumnBuilder.build());
            }
            //2.3构造MetadataDetail
            MetadataDetail metadataDetail = MetadataDetail.newBuilder()
                                                        .setMetadataSummary(metadataSummary)
                                                        .addAllMetadataColumns(metadataColumnList)
                                                        .setTotalTaskCount(0) //构建该元数据参与过得任务数 (已完成的和正在执行的)，todo 统计该元数据参与过的任务
                                                        .build();
            //2.4构建完整的请求信息
            MetaDataRpcMessage.PublishMetadataRequest request = MetaDataRpcMessage.PublishMetadataRequest.newBuilder().setInformation(metadataDetail).build();
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
            //2.1文件metaDataId
            MetaDataRpcMessage.RevokeMetadataRequest revokeMetadataRequest = MetaDataRpcMessage.RevokeMetadataRequest
                    .newBuilder()
                    .setMetadataId(metaDataId)
                    .build();
            //3.调用rpc,获取response
            SimpleResponse response = MetadataServiceGrpc.newBlockingStub(channel).revokeMetadata(revokeMetadataRequest);
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
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            MetaDataRpcMessage.GetMetadataDetailListResponse response = MetadataServiceGrpc.newBlockingStub(channel).getMetadataDetailList(request);
            //4.处理response
            if (response.getStatus() != GRPC_SUCCESS_CODE) {
                throw new ApplicationException(StrUtil.format("查询全网数据列表失败:{}",response.getMsg()));
            }
            List<MetaDataRpcMessage.GetMetadataDetailResponse> metaDataList = response.getMetadataListList();
            List<GlobalDataFileDetail> detailList = metaDataList.stream()
                    .map(metaData -> {
                        GlobalDataFileDetail detail = new GlobalDataFileDetail();
                        //元数据的拥有者
                        Organization owner = metaData.getOwner();
                        String identityId = owner.getIdentityId();
                        String orgName = owner.getNodeName();
                        detail.setIdentityId(identityId);
                        detail.setOrgName(orgName);
                        //元文件详情主体
                        MetadataDetail information = metaData.getInformation();
                        MetadataSummary summary = information.getMetadataSummary();
                        detail.setMetaDataId(summary.getMetadataId());
                        detail.setFileId(summary.getOriginId());
                        detail.setFileName(summary.getTableName());
                        detail.setResourceName(summary.getTableName());
                        detail.setRemarks(summary.getDesc());
                        detail.setFilePath(summary.getFilePath());
                        detail.setRows(summary.getRows());
                        detail.setColumns(summary.getColumns());
                        detail.setSize(Long.valueOf(summary.getSize()));
                        detail.setFileType(summary.getFileType().getNumber());
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
