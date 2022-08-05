package com.platon.datum.admin.grpc.client;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.protobuf.ByteString;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.AddressChangeUtil;
import com.platon.datum.admin.common.util.CryptoUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.common.util.WalletSignUtil;
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
import com.platon.datum.admin.grpc.entity.template.BaseMetaDataOption;
import com.platon.datum.admin.grpc.entity.template.ConsumeOption2;
import com.platon.datum.admin.grpc.entity.template.MetaDataOption1;
import com.platon.rlp.solidity.RlpEncoder;
import com.platon.rlp.solidity.RlpType;
import com.platon.utils.Numeric;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        CarrierEnum.OrigindataType origindataType = CarrierEnum.OrigindataType.forNumber(localDateFile.getFileType());
        BaseMetaDataOption metaDataOption = getMetaDataOption(origindataType, localDateFile, metaData);
        //2.2文件元数据摘要
        Metadata.MetadataSummary metadataSummary = Metadata.MetadataSummary.newBuilder()
                .setMetadataId("") //必须有个值
                .setMetadataName(metaData.getMetaDataName())
                .setMetadataType(CarrierEnum.MetadataType.forNumber(metaData.getMetaDataType()))
                .setDataHash(localDateFile.getDataHash())//原始数据内容的 sha256 的Hash
                .setDesc(metaData.getDesc())
                .setLocationType(CarrierEnum.DataLocationType.forNumber(localDateFile.getLocationType()))
                .setDataType(origindataType)
                .setIndustry(String.valueOf(metaData.getIndustry()))
                .setState(CarrierEnum.MetadataState.forNumber(metaData.getStatus()))
                .setMetadataOption(JSONUtil.toJsonStr(metaDataOption))//元数据选项 (json字符串, 根据 data_type 的值来配对对应的模板)
                .setUser(metaData.getUser())
                .setUserType(CarrierEnum.UserType.forNumber(metaData.getUserType()))
                .setSign(metaData.getSign() == null ? ByteString.EMPTY : ByteString.copyFrom(Numeric.hexStringToByteArray(metaData.getSign())))
                .build();
        //2.3构建完整的请求信息
        MetaDataRpcApi.PublishMetadataRequest request = MetaDataRpcApi.PublishMetadataRequest.newBuilder()
                .setInformation(metadataSummary)
                .build();
        log.debug("publishMetaData, request:{}", request);
        //3.调用rpc,获取response
        MetaDataRpcApi.PublishMetadataResponse response = MetadataServiceGrpc.newBlockingStub(channel)
                .publishMetadata(request);
        log.debug("publishMetaData, response:{}", response);
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

    public static void main(String[] args) throws IOException {
//        String sign = "0x0eead833cc96e135a8f99ffff64118fd600b76f1f6e4124ae5a9973d793beff1372c44484b833fa1a732779d88c2ac00da66417d99d8a6d3107cbd02c1d721521b";
//        String sha3Hex = "0xaa3655e73150ed90d1c1e051b487cc143ddf863ec6e8457a6310cafd36fa87a8";
//        Map<Integer, String> addresses = CryptoUtil.ecRecover(sign, Numeric.hexStringToByteArray(sha3Hex));
//        System.out.println(addresses.toString().contains(AddressChangeUtil.convert0xAddress("0xf795811af86e9f23a0c03de5115398b8d4778ed4").toLowerCase()));
//        boolean b = WalletSignUtil.verifySign(sha3Hex, "0x0eead833cc96e135a8f99ffff64118fd600b76f1f6e4124ae5a9973d793beff1372c44484b833fa1a732779d88c2ac00da66417d99d8a6d3107cbd02c1d721521b", "0xf795811af86e9f23a0c03de5115398b8d4778ed4");
//        System.out.println(b);

        String jsonStr = "{\"domain\":{\"name\":\"Datum\"},\"message\":{\"contents\":\"[\\\"bank_train_partyA\\\",1,\\\"7e7afd35ff1ebe5db3a06759a2d42293b8fc73f2b1dfd7e16527bddca9ed9584\\\",\\\"11111111111111111111111111111111111\\\",1,1,\\\"1\\\",1,\\\"{\\\\\\\"metadataColumns\\\\\\\":[{\\\\\\\"index\\\\\\\":1,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"CLIENT_ID\\\\\\\"},{\\\\\\\"index\\\\\\\":2,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DEFAULT\\\\\\\"},{\\\\\\\"index\\\\\\\":3,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"HOUSING\\\\\\\"},{\\\\\\\"index\\\\\\\":4,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"LOAN\\\\\\\"},{\\\\\\\"index\\\\\\\":5,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"CONTACT\\\\\\\"},{\\\\\\\"index\\\\\\\":6,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"CAMPAIGN\\\\\\\"},{\\\\\\\"index\\\\\\\":7,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"PDAYS\\\\\\\"},{\\\\\\\"index\\\\\\\":8,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"PREVIOUS\\\\\\\"},{\\\\\\\"index\\\\\\\":9,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"EURIBOR3M\\\\\\\"},{\\\\\\\"index\\\\\\\":10,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_APR\\\\\\\"},{\\\\\\\"index\\\\\\\":11,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_AUG\\\\\\\"},{\\\\\\\"index\\\\\\\":12,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_DEC\\\\\\\"},{\\\\\\\"index\\\\\\\":13,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_JUL\\\\\\\"},{\\\\\\\"index\\\\\\\":14,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_JUN\\\\\\\"},{\\\\\\\"index\\\\\\\":15,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_MAR\\\\\\\"},{\\\\\\\"index\\\\\\\":16,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_MAY\\\\\\\"},{\\\\\\\"index\\\\\\\":17,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_NOV\\\\\\\"},{\\\\\\\"index\\\\\\\":18,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_OCT\\\\\\\"},{\\\\\\\"index\\\\\\\":19,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"MONTH_SEP\\\\\\\"},{\\\\\\\"index\\\\\\\":20,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DAY_OF_WEEK_FRI\\\\\\\"},{\\\\\\\"index\\\\\\\":21,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DAY_OF_WEEK_MON\\\\\\\"},{\\\\\\\"index\\\\\\\":22,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DAY_OF_WEEK_THU\\\\\\\"},{\\\\\\\"index\\\\\\\":23,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DAY_OF_WEEK_TUE\\\\\\\"},{\\\\\\\"index\\\\\\\":24,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"DAY_OF_WEEK_WED\\\\\\\"},{\\\\\\\"index\\\\\\\":25,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"POUTCOME_FAILURE\\\\\\\"},{\\\\\\\"index\\\\\\\":26,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"POUTCOME_NONEXISTENT\\\\\\\"},{\\\\\\\"index\\\\\\\":27,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"POUTCOME_SUCCESS\\\\\\\"},{\\\\\\\"index\\\\\\\":28,\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\",\\\\\\\"size\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"Y\\\\\\\"}],\\\\\\\"columns\\\\\\\":28,\\\\\\\"rows\\\\\\\":45036,\\\\\\\"consumeOptions\\\\\\\":[],\\\\\\\"dataPath\\\\\\\":\\\\\\\"/home/user1/data/data_root/bank_train_partyA_20220802-092243.csv\\\\\\\",\\\\\\\"condition\\\\\\\":2,\\\\\\\"originId\\\\\\\":\\\\\\\"586c3768e2e972c4f04c5ca5f032d699d488d1941fd4fe20ba24eaac3579771a\\\\\\\",\\\\\\\"size\\\\\\\":3792331,\\\\\\\"consumeTypes\\\\\\\":[],\\\\\\\"hasTitle\\\\\\\":true}\\\"]\"},\"primaryType\":\"sign\",\"types\":{\"sign\":[{\"name\":\"contents\",\"type\":\"string\"}]}}";
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        boolean b = WalletSignUtil.verifyTypedDataV4(jsonStr, "0x703cfffc24f174c2ee6ffdfbf9e3256a82ebd8c0181f92b6d3aec9eb3240d35e2b7f3c26fd44591b81342445337e244d9bd9c927cecb40e2774d1dd4711b726f1c", "0xF795811af86E9f23A0c03dE5115398B8d4778eD4");
        System.out.println(b);
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
        CarrierEnum.OrigindataType origindataType = CarrierEnum.OrigindataType.forNumber(localDateFile.getFileType());
        BaseMetaDataOption metaDataOption = getMetaDataOption(origindataType, localDateFile, metaData);
        //2.2文件元数据摘要
        Metadata.MetadataSummary metadataSummary = Metadata.MetadataSummary.newBuilder()
                .setMetadataId(metaData.getMetaDataId()) //必须有个值
                .setMetadataName(metaData.getMetaDataName())
                .setMetadataType(CarrierEnum.MetadataType.forNumber(metaData.getMetaDataType()))
                .setDataHash(localDateFile.getDataHash())//原始数据内容的 sha256 的Hash
                .setDesc(metaData.getDesc())
                .setLocationType(CarrierEnum.DataLocationType.forNumber(localDateFile.getLocationType()))
                .setDataType(origindataType)
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

    public BaseMetaDataOption getMetaDataOption(CarrierEnum.OrigindataType origindataType, DataFile localDateFile, MetaData metaData) {
        BaseMetaDataOption metaDataOption = null;
        switch (origindataType) {
            case OrigindataType_CSV:
                metaDataOption = getMetaDataOption1(localDateFile, metaData);
                break;
            case UNRECOGNIZED:
            case OrigindataType_DIR:
            case OrigindataType_TXT:
            case OrigindataType_XLS:
            case OrigindataType_JSON:
            case OrigindataType_XLSX:
            case OrigindataType_BINARY:
            case OrigindataType_Unknown:
                throw new BizException(Errors.SysException, "Unsupported dataType:" + origindataType);
        }
        return metaDataOption;
    }

    private MetaDataOption1 getMetaDataOption1(DataFile localDateFile, MetaData metaData) {
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