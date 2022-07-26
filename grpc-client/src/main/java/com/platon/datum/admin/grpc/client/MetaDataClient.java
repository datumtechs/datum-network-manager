package com.platon.datum.admin.grpc.client;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.protobuf.ByteString;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.DataFileMapper;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.entity.*;
import com.platon.datum.admin.grpc.carrier.api.MetaDataRpcApi;
import com.platon.datum.admin.grpc.carrier.api.MetadataServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.carrier.types.Metadata;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.grpc.entity.template.ConsumeOption2;
import com.platon.datum.admin.grpc.entity.template.MetaDataOption1;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Resource
    private SimpleChannelManager channelManager;
    @Resource
    private DataTokenMapper dataTokenMapper;
    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

    /**
     * 上架或更新文件元数据
     *
     * @return 上架成功后的元数据ID
     */
    public String publishMetaData(MetaData metaData) {
        log.debug("从carrier发布元数据");
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        DataFile localDateFile = dataFileMapper.selectByFileId(metaData.getFileId());

        //2.拼装request
        //2.1
        MetaDataOption1 metaDataOption = getMetaDataOption(localDateFile, metaData);
        //2.2文件元数据摘要
        Metadata.MetadataSummary metadataSummary = Metadata.MetadataSummary.newBuilder()
                .setMetadataId("") //必须有个值
                .setMetadataName(metaData.getMetaDataName())
                .setMetadataType(CarrierEnum.MetadataType.forNumber(metaData.getMetaDataType()))
                .setDataHash(localDateFile.getDataHash())//原始数据内容的 sha256 的Hash
                .setDesc(metaData.getDesc())
                .setLocationType(CarrierEnum.DataLocationType.forNumber(localDateFile.getLocationType()))
                .setDataType(CarrierEnum.OrigindataType.forNumber(localDateFile.getFileType()))
                .setIndustry(String.valueOf(metaData.getIndustry()))
                .setState(CarrierEnum.MetadataState.forNumber(metaData.getStatus()))
                .setMetadataOption(JSONUtil.toJsonStr(metaDataOption))//元数据选项 (json字符串, 根据 data_type 的值来配对对应的模板)
                .setUser(metaData.getUser())
                .setUserType(CarrierEnum.UserType.forNumber(metaData.getUserType()))
                .setSign(ByteString.copyFromUtf8(metaData.getSign()))
                .build();
        //2.3构建完整的请求信息
        MetaDataRpcApi.PublishMetadataRequest request = MetaDataRpcApi.PublishMetadataRequest.newBuilder()
                .setInformation(metadataSummary)
                .build();
        //3.调用rpc,获取response
        MetaDataRpcApi.PublishMetadataResponse response = MetadataServiceGrpc.newBlockingStub(channel)
                .publishMetadata(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        if (StringUtils.isBlank(response.getMetadataId())) {
            log.error("the metadata ID returned from Carrier is blank.");
            throw new CallGrpcServiceFailed();
        }

        return response.getMetadataId();
    }

    /**
     * 下架文件元数据
     *
     * @param metaDataId 文件metaDataId
     * @return 上架成功后的元数据ID
     */
    public void revokeMetaData(String metaDataId) {
        log.debug("从carrier撤消元数据，metaDataId:{}", metaDataId);
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        //2.1文件metaDataId
        MetaDataRpcApi.RevokeMetadataRequest revokeMetadataRequest = MetaDataRpcApi.RevokeMetadataRequest
                .newBuilder()
                .setMetadataId(metaDataId)
                .build();
        //3.调用rpc,获取response
        Common.SimpleResponse response = MetadataServiceGrpc.newBlockingStub(channel)
                .revokeMetadata(revokeMetadataRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

    }

    /**
     * 这个只会从数据中心同步已经发布的元数据
     *
     * @param latestSynced
     * @return
     */
    public List<Pair<MetaData, DataToken>> getLocalMetaDataList(LocalDateTime latestSynced) {

        log.debug("从carrier查询元数据列表，latestSynced:{}", latestSynced);
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        MetaDataRpcApi.GetLocalMetadataDetailListRequest request = MetaDataRpcApi.GetLocalMetadataDetailListRequest
                .newBuilder()
                .setLastUpdated(LocalDateTimeUtil.getTimestamp(latestSynced))
                .setPageSize(GrpcConstant.PageSize)
                .build();
        //3.调用rpc,获取response
        MetaDataRpcApi.GetLocalMetadataDetailListResponse response = MetadataServiceGrpc.newBlockingStub(channel).getLocalMetadataDetailList(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        log.debug("从carrier查询元数据列表，数量:{}", response.getMetadatasList().size());

        List<MetaDataRpcApi.GetLocalMetadataDetail> metaDataList = response.getMetadatasList();
        return metaDataList.stream()
                .map(localMetaDataDetail -> {
                    MetaData metaData = new MetaData();
                    DataToken dataToken = new DataToken();
                    Metadata.MetadataSummary summary = localMetaDataDetail.getInformation().getMetadataSummary();
                    metaData.setMetaDataId(summary.getMetadataId());
                    metaData.setStatus(summary.getState().getNumber());
                    metaData.setPublishTime(LocalDateTimeUtil.getLocalDateTime(summary.getPublishAt()));
                    metaData.setRecUpdateTime(LocalDateTimeUtil.getLocalDateTime(summary.getUpdateAt()));

                    //v0.5.0 add
                    processConsumeOption(summary, metaData, dataToken);
                    Pair<MetaData, DataToken> pair = Pair.of(metaData, dataToken);
                    return pair;
                })
                .collect(Collectors.toList());
    }

    /**
     * v0.5.0更新元数据
     */
    public void updateMetadata(MetaData metaData) {
        log.debug("carrier更新元数据，metaDataId:{}", metaData.getMetaDataId());
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        DataFile localDateFile = dataFileMapper.selectByFileId(metaData.getFileId());

        //2.拼装request
        //2.1
        MetaDataOption1 metaDataOption = getMetaDataOption(localDateFile, metaData);
        //2.2文件元数据摘要
        Metadata.MetadataSummary metadataSummary = Metadata.MetadataSummary.newBuilder()
                .setMetadataId(metaData.getMetaDataId()) //必须有个值
                .setMetadataName(metaData.getMetaDataName())
                .setMetadataType(CarrierEnum.MetadataType.forNumber(metaData.getMetaDataType()))
                .setDataHash(localDateFile.getDataHash())//原始数据内容的 sha256 的Hash
                .setDesc(metaData.getDesc())
                .setLocationType(CarrierEnum.DataLocationType.forNumber(localDateFile.getLocationType()))
                .setDataType(CarrierEnum.OrigindataType.forNumber(localDateFile.getFileType()))
                .setIndustry(String.valueOf(metaData.getIndustry()))
                .setState(CarrierEnum.MetadataState.forNumber(metaData.getStatus()))
                .setMetadataOption(JSONUtil.toJsonStr(metaDataOption))//元数据选项 (json字符串, 根据 data_type 的值来配对对应的模板)
                .setUser(metaData.getUser())
                .setUserType(CarrierEnum.UserType.forNumber(metaData.getUserType()))
                .setSign(ByteString.copyFromUtf8(metaData.getSign()))
                .build();
        //2.4构建完整的请求信息
        MetaDataRpcApi.UpdateMetadataRequest request = MetaDataRpcApi.UpdateMetadataRequest.newBuilder()
                .setInformation(metadataSummary)
                .build();
        //3.调用rpc,获取response
        Common.SimpleResponse response = MetadataServiceGrpc.newBlockingStub(channel).updateMetadata(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    private MetaDataOption1 getMetaDataOption(DataFile localDateFile, MetaData metaData) {
        MetaDataOption1 metaDataOption1 = new MetaDataOption1();
        metaDataOption1.setOriginId(localDateFile.getFileId());
        metaDataOption1.setDataPath(localDateFile.getFilePath());
        metaDataOption1.setColumns(localDateFile.getColumns());
        metaDataOption1.setRows(localDateFile.getRows());
        metaDataOption1.setSize(localDateFile.getSize());
        metaDataOption1.setHasTitle(localDateFile.getHasTitle());
        //元数据列信息
        List<MetaDataOption1.MetadataColumn> metaDataColumns = getMetaDataColumns(metaData);
        metaDataOption1.setMetadataColumns(metaDataColumns);
        //元数据消费信息，包括erc20和erc721地址和消费量
        Pair<List<Integer>, List<String>> consumeTypesAndOptions = getConsumeTypesAndOptions(metaData);
        metaDataOption1.setConsumeTypes(consumeTypesAndOptions.getLeft());
        metaDataOption1.setConsumeOptions(consumeTypesAndOptions.getRight());
        Integer usage = metaData.getUsage();
        switch (usage) {
            case 1:
                metaDataOption1.setCondition(1);
                break;
            case 2:
                metaDataOption1.setCondition(2);
                break;
            case 3:
                metaDataOption1.setCondition(3);
                break;
            default:
                throw new BizException(Errors.SysException, "Unknown usage:" + usage);
        }
        return metaDataOption1;
    }

    private List<MetaDataOption1.MetadataColumn> getMetaDataColumns(MetaData metaData) {
        List<MetaDataOption1.MetadataColumn> metadataColumns = new ArrayList<>();
        List<MetaDataColumn> columnList = metaData.getMetaDataColumnList();
        for (int i = 0; i < columnList.size(); i++) {
            MetaDataColumn metaDataColumn = columnList.get(i);
            MetaDataOption1.MetadataColumn column = new MetaDataOption1.MetadataColumn();
            column.setIndex(metaDataColumn.getColumnIdx());
            if (StringUtils.isNotBlank(metaDataColumn.getColumnName())) {
                column.setName(metaDataColumn.getColumnName());
            }
            if (StringUtils.isNotBlank(metaDataColumn.getColumnType())) {
                column.setType(metaDataColumn.getColumnType());
            }
            column.setSize(metaDataColumn.getSize() == null ? 0 : metaDataColumn.getSize());
            if (StringUtils.isNotBlank(metaDataColumn.getRemarks())) {
                column.setComment(metaDataColumn.getRemarks());
            }
            metadataColumns.add(column);
        }
        return metadataColumns;
    }

    private Pair<List<Integer>, List<String>> getConsumeTypesAndOptions(MetaData metaData) {
        List<Integer> consumeTypes = new ArrayList<>();
        List<String> consumeOptions = new ArrayList<>();

        //erc20
        DataToken dataToken = dataTokenMapper.selectPublishedByMetaDataId(metaData.getId());
        if (dataToken != null) {
            consumeTypes.add(2);
            ConsumeOption2 consumeOption2 = new ConsumeOption2();
            consumeOption2.setContract(dataToken.getAddress());
            consumeOption2.setCryptoAlgoConsumeUnit(Long.valueOf(dataToken.getNewCiphertextFee()));
            consumeOption2.setPlainAlgoConsumeUnit(Long.valueOf(dataToken.getNewPlaintextFee()));

            //支持多个erc20，v0.5.0暂时只支持一个
            List<ConsumeOption2> optionList = new ArrayList<>();
            optionList.add(consumeOption2);
            JSONArray objects = JSONUtil.parseArray(optionList);
            consumeOptions.add(objects.toString());
        }

        //erc721
        AttributeDataToken attributeDataToken = attributeDataTokenMapper.selectPublishedByMetaDataId(metaData.getId());
        if (attributeDataToken != null) {
            consumeTypes.add(3);

            //支持多个erc721，v0.5.0暂时只支持一个
            List<String> addressList = new ArrayList<>();
            addressList.add(attributeDataToken.getAddress());
            JSONArray objects = JSONUtil.parseArray(addressList);
            consumeOptions.add(objects.toString());
        }

        return Pair.of(consumeTypes, consumeOptions);
    }

    private void processConsumeOption(Metadata.MetadataSummary summary, MetaData metaData, DataToken dataToken) {
        String metadataOption = summary.getMetadataOption();
        int dataTypeValue = summary.getDataTypeValue();
        switch (dataTypeValue) {
            case 1:
                processDataType1(metadataOption, metaData, dataToken);
                break;
            default:
                throw new BizException(Errors.SysException, "Unknown dataTypeValue: " + dataTypeValue);
        }
    }

    private void processDataType1(String metadataOption, MetaData metaData, DataToken dataToken) {
        MetaDataOption1 metaDataOption1 = JSONUtil.toBean(metadataOption, MetaDataOption1.class);
        List<Integer> consumeTypes = metaDataOption1.getConsumeTypes();
        List<String> consumeOptions = metaDataOption1.getConsumeOptions();
        for (int i = 0; i < consumeTypes.size(); i++) {
            String option = consumeOptions.get(i);
            JSONArray jsonArray = JSONUtil.parseArray(option);
            Integer type = consumeTypes.get(i);
            switch (type) {
                case 1:
                    break;
                case 2:
                    //0.5.0暂时只支持1个erc20
                    List<ConsumeOption2> erc20List = jsonArray.toList(ConsumeOption2.class);
                    if (!erc20List.isEmpty()) {
                        ConsumeOption2 consumeOption2 = erc20List.get(0);
                        metaData.setDataTokenAddress(consumeOption2.getContract());
                        dataToken.setAddress(consumeOption2.getContract());
                        dataToken.setCiphertextFee(consumeOption2.getCryptoAlgoConsumeUnit().toString());
                        dataToken.setPlaintextFee(consumeOption2.getPlainAlgoConsumeUnit().toString());
                    }
                    break;
                case 3:
                    //0.5.0暂时只支持1个erc721
                    List<String> erc721List = jsonArray.toList(String.class);
                    if (!erc721List.isEmpty()) {
                        metaData.setAttributeDataTokenAddress(erc721List.get(0));
                    }
                    break;
                default:
                    throw new BizException(Errors.SysException, "Unsupported consumeType:" + type);
            }
        }
    }

}