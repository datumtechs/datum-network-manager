package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.FileTypeEnum;
import com.platon.metis.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.service.MetaDataRpcMessage;
import com.platon.metis.admin.grpc.service.MetadataServiceGrpc;
import com.platon.metis.admin.grpc.types.Metadata;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private SimpleChannelManager channelManager;

    /**
     * 上架或更新文件元数据
     * @param localDateFile 文件详情
     * @return 上架成功后的元数据ID
     */
    public String publishMetaData(LocalDataFile localDateFile, LocalMetaData localMetaData){
        log.debug("从carrier发布元数据");
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        //2.1文件元数据摘要
        CommonBase.OriginFileType fileType = CommonBase.OriginFileType.forNumber(localDateFile.getFileType() == 0 ? FileTypeEnum.FILETYPE_UNKONW.getValue() : FileTypeEnum.FILETYPE_CSV.getValue());
        Metadata.MetadataSummary metadataSummary = Metadata.MetadataSummary.newBuilder()
                .setMetadataId("") //必须有个值
                .setOriginId(localDateFile.getFileId())
                .setTableName(localMetaData.getMetaDataName())
                .setDesc(localMetaData.getRemarks())
                .setFilePath(localDateFile.getFilePath())
                .setRows(localDateFile.getRows())
                .setColumns(localDateFile.getColumns())
                .setSize(localDateFile.getSize())
                .setFileType(fileType)
                .setHasTitle(localDateFile.getHasTitle())
                .setIndustry(String.valueOf(localMetaData.getIndustry()))
                .setState(CommonBase.MetadataState.forNumber(localMetaData.getStatus()))
                .build();
        //2.2文件元数据对应原始文件对外暴露的列描述列表
        List<Metadata.MetadataColumn> metadataColumnList = new ArrayList<>();
        List<LocalMetaDataColumn> columnList = localMetaData.getLocalMetaDataColumnList();
        for (int i = 0; i < columnList.size(); i++) {
            LocalMetaDataColumn metaDataColumn = columnList.get(i);
            Metadata.MetadataColumn.Builder metadataColumnBuilder = Metadata.MetadataColumn.newBuilder();
            metadataColumnBuilder.setCIndex(metaDataColumn.getColumnIdx());
            if (StringUtils.isNotBlank(metaDataColumn.getColumnName())){
                metadataColumnBuilder.setCName(metaDataColumn.getColumnName());
            }
            if (StringUtils.isNotBlank(metaDataColumn.getColumnType())){
                metadataColumnBuilder.setCType(metaDataColumn.getColumnType());
            }
            metadataColumnBuilder.setCSize(metaDataColumn.getSize() == null ? 0 : metaDataColumn.getSize());
            if (StringUtils.isNotBlank(metaDataColumn.getRemarks())){
                metadataColumnBuilder.setCComment(metaDataColumn.getRemarks());
            }
            metadataColumnList.add(metadataColumnBuilder.build());
        }
        //2.3构造MetadataDetail
        Metadata.MetadataDetail metadataDetail = Metadata.MetadataDetail.newBuilder()
                .setMetadataSummary(metadataSummary)
                .addAllMetadataColumns(metadataColumnList)
                .setTotalTaskCount(0) //构建该元数据参与过得任务数 (已完成的和正在执行的)，todo 统计该元数据参与过的任务
                .build();
        //2.4构建完整的请求信息
        MetaDataRpcMessage.PublishMetadataRequest request = MetaDataRpcMessage.PublishMetadataRequest.newBuilder().setInformation(metadataDetail).build();
        //3.调用rpc,获取response
        MetaDataRpcMessage.PublishMetadataResponse response = MetadataServiceGrpc.newBlockingStub(channel).publishMetadata(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        if(StringUtils.isBlank(response.getMetadataId())){
            log.error("the metadata ID returned from Carrier is blank.");
            throw new CallGrpcServiceFailed();
        }

        return response.getMetadataId();
    }

    /**
     * 下架文件元数据
     * @param metaDataId 文件metaDataId
     * @return 上架成功后的元数据ID
     */
    public void revokeMetaData(String metaDataId){
        log.debug("从carrier撤消元数据，metaDataId:{}",metaDataId);
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        //2.1文件metaDataId
        MetaDataRpcMessage.RevokeMetadataRequest revokeMetadataRequest = MetaDataRpcMessage.RevokeMetadataRequest
                .newBuilder()
                .setMetadataId(metaDataId)
                .build();
        //3.调用rpc,获取response
        CommonBase.SimpleResponse response = MetadataServiceGrpc.newBlockingStub(channel).revokeMetadata(revokeMetadataRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

    }

    /**
     * 查询全网数据列表
     * @return
     */
    public List<GlobalDataFile> getGlobalMetaDataList(LocalDateTime latestSynced) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        MetaDataRpcMessage.GetGlobalMetadataDetailListRequest request = MetaDataRpcMessage.GetGlobalMetadataDetailListRequest
                .newBuilder()
                .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();
        //3.调用rpc,获取response
        MetaDataRpcMessage.GetGlobalMetadataDetailListResponse response = MetadataServiceGrpc.newBlockingStub(channel).getGlobalMetadataDetailList(request);
        //4.处理response
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        List<MetaDataRpcMessage.GetGlobalMetadataDetailResponse> metaDataList = response.getMetadataListList();
        List<GlobalDataFile> globalDataFileList = metaDataList.stream()
                .map(globalMetadataDetailResponse -> {
                    GlobalDataFile globalDataFile = new GlobalDataFile();
                    //元数据的拥有者
                    CommonBase.Organization owner = globalMetadataDetailResponse.getOwner();
                    String identityId = owner.getIdentityId();
                    String orgName = owner.getNodeName();
                    globalDataFile.setIdentityId(identityId);
                    globalDataFile.setOrgName(orgName);
                    //元文件详情主体
                    Metadata.MetadataDetail information = globalMetadataDetailResponse.getInformation();
                    Metadata.MetadataSummary summary = information.getMetadataSummary();
                    globalDataFile.setMetaDataId(summary.getMetadataId());
                    globalDataFile.setFileId(summary.getOriginId());
                    globalDataFile.setFileName(summary.getTableName());
                    globalDataFile.setResourceName(summary.getTableName());
                    globalDataFile.setRemarks(summary.getDesc());
                    globalDataFile.setFilePath(summary.getFilePath());
                    globalDataFile.setRows(summary.getRows());
                    globalDataFile.setColumns(summary.getColumns());
                    globalDataFile.setSize(summary.getSize());
                    globalDataFile.setFileType(summary.getFileType().getNumber());
                    globalDataFile.setHasTitle(summary.getHasTitle());
                    globalDataFile.setStatus(summary.getState().getNumber());
                    globalDataFile.setPublishTime(Date.from(Instant.ofEpochMilli(summary.getPublishAt())));
                    globalDataFile.setUpdateTime(Date.from(Instant.ofEpochMilli(summary.getUpdateAt())));

                    information.getMetadataColumnsList().forEach(columnDetail -> {
                        GlobalMetaDataColumn metaDataColumn = new GlobalMetaDataColumn();
                        metaDataColumn.setMetaDataId(summary.getMetadataId());
                        metaDataColumn.setColumnIdx(columnDetail.getCIndex());
                        metaDataColumn.setColumnName(columnDetail.getCName());
                        metaDataColumn.setColumnType(columnDetail.getCType());
                        metaDataColumn.setSize(columnDetail.getCSize());
                        metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                        metaDataColumn.setRemarks(columnDetail.getCComment());
                        globalDataFile.getMetaDataColumnList().add(metaDataColumn);
                    });
                    return globalDataFile;
                })
                .collect(Collectors.toList());
        return globalDataFileList;
    }

    public List<LocalMetaData> getLocalMetaDataList(LocalDateTime latestSynced) {

        log.debug("从carrier查询元数据列表，latestSynced:{}",latestSynced);
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        MetaDataRpcMessage.GetLocalMetadataDetailListRequest request = MetaDataRpcMessage.GetLocalMetadataDetailListRequest
                .newBuilder()
                .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                .setPageSize(GrpcConstant.PageSize)
                .build();
        //3.调用rpc,获取response
        MetaDataRpcMessage.GetLocalMetadataDetailListResponse response = MetadataServiceGrpc.newBlockingStub(channel).getLocalMetadataDetailList(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        log.debug("从carrier查询元数据列表，数量:{}",response.getMetadataListList().size());

        List<MetaDataRpcMessage.GetLocalMetadataDetailResponse> metaDataList = response.getMetadataListList();
        return metaDataList.stream().map(localMetaDataDetail -> {
            Metadata.MetadataSummary summary = localMetaDataDetail.getInformation().getMetadataSummary();
            LocalMetaData localMetaData = new LocalMetaData();
            localMetaData.setStatus(summary.getState().getNumber());
            localMetaData.setPublishTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(summary.getPublishAt()), ZoneOffset.UTC));
            localMetaData.setRecUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(summary.getUpdateAt()), ZoneOffset.UTC));
            return localMetaData;
        }).collect(Collectors.toList());
    }
}