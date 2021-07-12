package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage;
import com.platon.rosettanet.admin.grpc.service.MetaDataServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    public String publishMetaData(LocalDataFileDetail fileDetail){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        //2.1文件所属组织者信息
        LocalOrg localOrgInfo = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        CommonMessage.OrganizationIdentityInfo orgInfo = CommonMessage.OrganizationIdentityInfo
                .newBuilder()
                .setName(localOrgInfo.getName())
                .setNodeId(localOrgInfo.getCarrierNodeId())
                .setIdentityId(localOrgInfo.getIdentityId())
                .build();
        //2.2文件元数据信息
        MetaDataRpcMessage.MetaDataSummary summary = MetaDataRpcMessage.MetaDataSummary
                .newBuilder()
                .setMetaDataId(fileDetail.getMetaDataId())
                .setOriginId(fileDetail.getFileId())
                .setTableName(fileDetail.getResourceName())
                .setDesc(fileDetail.getRemarks())
                .setFilePath(fileDetail.getFilePath())
                .setRows(fileDetail.getRows().intValue()) // 源文件的行数
                .setColumns(fileDetail.getColumns())
                .setSize(fileDetail.getSize().intValue())
                .setFileType(fileDetail.getFileType())
                .setHasTitle(fileDetail.getHasTitle())
                .setState(fileDetail.getStatus())// 元数据的状态 (create: 还未发布的新表; release: 已发布的表; revoke: 已撤销的表)
                .build();

        MetaDataRpcMessage.MetaDataDetailShow.Builder builder = MetaDataRpcMessage.MetaDataDetailShow
                .newBuilder()
                .setMetaDataSummary(summary);
        //2.3构建文件元数据列信息
        List<LocalMetaDataColumn> columnList = fileDetail.getLocalMetaDataColumnList();
        for (int i = 0; i < columnList.size(); i++) {
            LocalMetaDataColumn metaDataColumn = columnList.get(i);
            MetaDataRpcMessage.MetaDataColumnDetail columnDetail = MetaDataRpcMessage.MetaDataColumnDetail
                    .newBuilder()
                    .setCindex(metaDataColumn.getColumnIdx())
                    .setCname(metaDataColumn.getColumnName())
                    .setCtype(metaDataColumn.getColumnType())
                    .setCsize(metaDataColumn.getSize().intValue())
                    .setCcomment(metaDataColumn.getRemarks())
                    .build();
            builder.setColumnMeta(i,columnDetail);
        }
        //2.4收集完成文件信息
        MetaDataRpcMessage.MetaDataDetailShow metaDataDetail = builder.build();
        //2.5构建完整的请求信息
        MetaDataRpcMessage.PublishMetaDataRequest request = MetaDataRpcMessage.PublishMetaDataRequest
                .newBuilder()
                .setOwner(orgInfo)
                .setInformation(metaDataDetail)
                .build();
        //3.调用rpc,获取response
        MetaDataRpcMessage.PublishMetaDataResponse response = MetaDataServiceGrpc.newBlockingStub(channel).publishMetaData(request);
        //4.处理response
        if (response.getStatus() != 0) {
            throw new ApplicationException(StrUtil.format("元数据信息发布失败:{}",response.getMsg()));
        }
        return response.getMetaDataId();
    }

    /**
     * 下架文件元数据
     * @param metaDataId 文件metaDataId
     * @return 上架成功后的元数据ID
     */
    public void revokeMetaData(String metaDataId){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        //2.1文件所属组织者信息
        LocalOrg localOrgInfo = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        CommonMessage.OrganizationIdentityInfo orgInfo = CommonMessage.OrganizationIdentityInfo
                .newBuilder()
                .setName(localOrgInfo.getName())
                .setNodeId(localOrgInfo.getCarrierNodeId())
                .setIdentityId(localOrgInfo.getIdentityId())
                .build();
        MetaDataRpcMessage.RevokeMetaDataRequest request = MetaDataRpcMessage.RevokeMetaDataRequest
                .newBuilder()
                .setOwner(orgInfo)
                .setMetaDataId(metaDataId)
                .build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode response = MetaDataServiceGrpc.newBlockingStub(channel).revokeMetaData(request);
        //4.处理response
        if (response.getStatus() != 0) {
            throw new ApplicationException(StrUtil.format("元数据信息下架失败:{}",response.getMsg()));
        }
    }

    /**
     * 查询全网数据列表
     * @return
     */
    public List<GlobalDataFileDetail> getMetaDataDetailList(){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        MetaDataRpcMessage.GetMetaDataDetailListResponse response = MetaDataServiceGrpc.newBlockingStub(channel).getMetaDataDetailList(request);
        //4.处理response
        if (response.getStatus() != 0) {
            throw new ApplicationException(StrUtil.format("查询全网数据列表失败:{}",response.getMsg()));
        }
        List<MetaDataRpcMessage.GetMetaDataDetailResponse> metaDataList = response.getMetaDataListList();
        List<GlobalDataFileDetail> detailList = metaDataList.stream()
                .map(metaData -> {
                    GlobalDataFileDetail detail = new GlobalDataFileDetail();
                    //元数据的拥有者
                    CommonMessage.OrganizationIdentityInfo owner = metaData.getOwner();
                    String identityId = owner.getIdentityId();
                    detail.setIdentityId(identityId);
                    //元文件详情主体
                    MetaDataRpcMessage.MetaDataDetailShow information = metaData.getInformation();
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
                    MetaDataRpcMessage.MetaDataSummary summary = information.getMetaDataSummary();
                    detail.setMetaDataId(summary.getMetaDataId());
                    detail.setFileId(summary.getOriginId());
                    detail.setResourceName(summary.getTableName());
                    detail.setRemarks(summary.getDesc());
                    detail.setFilePath(summary.getFilePath());
                    detail.setRows(Long.valueOf(summary.getRows()));
                    detail.setColumns(summary.getColumns());
                    detail.setSize(Long.valueOf(summary.getSize()));
                    detail.setFileType(summary.getFileType());
                    detail.setHasTitle(summary.getHasTitle() == false ? (byte) 0 : (byte) 1);
                    detail.setStatus(summary.getState());
                    //            uint32 cindex   = 1;                         // 列的索引
                    //            string cname    = 2;                          // 列名
                    //            string ctype    = 3;                          // 列类型
                    //            uint32 csize    = 4;                          // 列大小(单位: byte)
                    //            string ccomment = 5;                       // 列描述
                    information.getColumnMetaList().forEach(columnDetail -> {
                        GlobalMetaDataColumn metaDataColumn = new GlobalMetaDataColumn();
                        metaDataColumn.setMetaDataId(summary.getMetaDataId());
                        metaDataColumn.setColumnIdx(columnDetail.getCindex());
                        metaDataColumn.setColumnName(columnDetail.getCname());
                        metaDataColumn.setColumnType(columnDetail.getCtype());
                        metaDataColumn.setSize(Long.valueOf(columnDetail.getCsize()));
                        metaDataColumn.setRemarks(columnDetail.getCcomment());
                        detail.getMetaDataColumnList().add(metaDataColumn);
                    });

                    return detail;
                })
                .collect(Collectors.toList());
        return detailList;
    }

}
