package com.platon.datum.admin.controller.data;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.*;
import com.platon.datum.admin.common.util.AddressTypeUtil;
import com.platon.datum.admin.common.util.NameUtil;
import com.platon.datum.admin.controller.BaseController;
import com.platon.datum.admin.dao.DataFileMapper;
import com.platon.datum.admin.dao.MetaDataColumnMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataFile;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.dao.entity.MetaDataColumn;
import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dao.enums.DataAddTypeEnum;
import com.platon.datum.admin.dao.enums.DataFileStatusEnum;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.MetaDataDetailResp;
import com.platon.datum.admin.dto.resp.MetaDataImportFileResp;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.service.MetaDataService;
import com.platon.datum.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/8 23:14
 * @Version
 * @Desc 我的数据
 */


@Api(tags = "我的数据")
@RestController
@RequestMapping("/api/v1/data/")
@Slf4j
public class MetaDataController extends BaseController {

    @Resource
    private MetaDataService metaDataService;

    @Resource
    private TaskService taskService;

    @Resource
    private DataFileMapper dataFileMapper;

    @Resource
    private MetaDataMapper metaDataMapper;
    @Resource
    private MetaDataColumnMapper metaDataColumnMapper;


    /**
     * 根据关键字查询钱包自身的元数据列表摘要信息
     */
    @ApiOperation(value = "数据列表关键字查询")
    @PostMapping("/listLocalMetaDataByKeyword")
    public JsonResponse<List<MetaData>> listLocalMetaDataByKeyword(@RequestBody @Validated MetaDataMetaDataListByKeyWordReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        Page<MetaData> metaDataPage = metaDataService.listMetaData(req.getPageNumber(), req.getPageSize(), req.getKeyword(), userAddress, req.getStatus());
        return JsonResponse.page(metaDataPage);
    }

    /**
     * 根据关键字查询钱包自身的元数据列表摘要信息，状态为未绑定凭证Id的数据
     */
    @ApiOperation(value = "关键字查询未发布无属性凭证的元数据")
    @PostMapping("/listMetaDataUnPublishDataTokenByKeyword")
    public JsonResponse<List<MetaData>> listMetaDataUnPublishDataTokenByKeyword(@RequestBody @Validated MetaDataListMetaDataUnPublishDataTokenByKeywordReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        List<MetaData> metaDataPage = metaDataService.listMetaDataUnPublishDataToken(req.getKeyword(), userAddress);
        return JsonResponse.success(metaDataPage);
    }

    @ApiOperation(value = "关键字查询未发布有属性凭证的元数据")
    @PostMapping("/listMetaDataUnPublishAttributeDataTokenByKeyword")
    public JsonResponse<List<MetaData>> listMetaDataUnPublishAttributeDataTokenByKeyword(@RequestBody @Validated MetaDataListMetaDataUnPublishDataTokenByKeywordReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        List<MetaData> metaDataPage = metaDataService.listMetaDataUnPublishAttributeDataToken(req.getKeyword(), userAddress);
        return JsonResponse.success(metaDataPage);
    }


    /**
     * 根据数据id查询数据参与的任务信息列表
     */
    @ApiOperation(value = "数据参与的任务信息列表", notes = "返回数据的每个task中，包含如下的动态字段：</br>taskSponsor, powerProvider, dataProvider, resultConsumer, algoProvider </br>当这些字段值等于1时表示本组织在任务中的相应角色")
    @PostMapping("/listTaskByMetaDataId")
    public JsonResponse<List<Task>> listTaskByMetaDataId(@RequestBody @Validated DataJoinTaskListReq req) {
        Page<Task> localDataJoinTaskPage = taskService.listTaskByIdentityIdAndMetaDataIdWithRole(OrgCache.getLocalOrgIdentityId(), req.getMetaDataId(), req.getPageNumber(), req.getPageSize());
        return JsonResponse.page(localDataJoinTaskPage, localDataJoinTaskPage);
    }


    /**
     * 导入文件，并解析文件格式和内容，把文件信息存入local_data_file，把文件列信息返回给前端。
     * 注意：此时正常逻辑应该把列信息存入local_data_file_column，但是这里并没有。
     * 为了简化后续操作的数据查询，只是在添加元数据时，把全量的列信息存入local_meta_data_column表中。
     * 因此，local_data_file_column表其实没有用了。
     */
    @ApiOperation(value = "导入文件")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "hasTitle", value = "是否包含表头", required = true, paramType = "query", example = "true"),
    })
    @PostMapping("/uploadFile")
    public JsonResponse<MetaDataImportFileResp> importFile(MultipartFile file) {
        //v0.5.0默认上传的csv文件都有文件头
        boolean hasTitle = true;
        //校验文件
        DataFile dataFile = metaDataService.uploadFile(file, hasTitle);
        MetaDataImportFileResp resp = MetaDataImportFileResp.from(dataFile);
        return JsonResponse.success(resp);
    }


    /**
     * 元数据添加：
     * 在添加元数据时，需要把元数据信息存入local_meta_data表，并把全量的列信息存入local_meta_data_column表中（带visible）。
     */
    @ApiOperation(value = "添加数据/另存为新数据")
    @PostMapping("/addLocalMetaData")
    public JsonResponse addLocalMetaData(@RequestBody @Validated AddLocalMetaDataReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        //判断数据添加类型
        if (req.getAddType() != DataAddTypeEnum.ADD.getType() && req.getAddType() != DataAddTypeEnum.ADD_AGAIN.getType()) {
            log.error("AddLocalMetaDataReq.type error:{}", req.getAddType());
            throw new ArgumentException();
        }
        //判断格式是否对
        if (!NameUtil.isValidName(req.getResourceName())) {
            log.error("AddLocalMetaDataReq.resourceName error:{}", req.getResourceName());
            throw new MetadataResourceNameIllegal();
        }
        //判断是否重复
        boolean exist = metaDataService.isExistResourceName(req.getResourceName(), userAddress);
        if (exist) {
            log.error("AddLocalMetaDataReq.resourceName error:{}", req.getResourceName());
            throw new MetadataResourceNameExists();
        }

        DataFile dataFile = new DataFile();
        BeanUtils.copyProperties(req, dataFile);

        MetaData metaData = new MetaData();
        metaData.setFileId(req.getFileId());
        metaData.setDesc(req.getDesc());
        metaData.setIndustry(req.getIndustry());
        metaData.setMetaDataName(req.getResourceName());
        metaData.setStatus(DataFileStatusEnum.CREATED.getStatus());//添加数据状态为created
        metaData.setUser(userAddress);
        metaData.setUserType(AddressTypeUtil.getType(userAddress));
        metaData.setMetaDataType(CarrierEnum.MetadataType.MetadataType_DataFile_VALUE);
        metaData.setUsage(req.getUsage());

        metaData.setMetaDataColumnList(req.getMetaDataColumnList());

        metaDataService.addLocalMetaData(metaData);

        return JsonResponse.success();
    }

    /**
     * 查看元数据详情
     */
    @ApiOperation(value = "查看元数据详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "元数据db key", required = true, paramType = "query", example = "1"),
    })
    @GetMapping("/localMetaDataInfo")
    public JsonResponse<MetaDataDetailResp> detail(@Validated @NotNull(message = "id不为空") Integer id) {
        //查询localMetaData，并查询出taskCount放入动态字段
        MetaData metaData = metaDataMapper.findWithTaskCount(id);
        if (metaData == null) {
            throw new ObjectNotFound();
        }

        //查询元数据的localMetaDataColumnList
        List<MetaDataColumn> metaDataColumnList = metaDataColumnMapper.selectByLocalMetaDataDbId(id);
        metaData.setMetaDataColumnList(metaDataColumnList);

        //查询元数据对应localDataFile以及localDataFileColumnList，并把localDataFileColumnList中属于localMetaDataColumnList的字段做标识
        DataFile dataFile = dataFileMapper.selectByFileId(metaData.getFileId());

        MetaDataDetailResp resp = MetaDataDetailResp.from(dataFile, metaData);
        return JsonResponse.success(resp);
    }

    /**
     * 修改数据信息
     */
    @ApiOperation(value = "修改元数据信息")
    @PostMapping("/updateLocalMetaData")
    public JsonResponse update(@RequestBody @Validated MetaDataUpdateReq req) {
        MetaData metaData = new MetaData();
        metaData.setDesc(req.getDesc());
        metaData.setIndustry(req.getIndustry());
        metaData.setId(req.getId());

        //校验列名和类型不可为空
        List<MetaDataColumn> metaDataColumnList = req.getMetaDataColumnList();
        metaDataColumnList.forEach(localMetaDataColumn -> {
            String columnName = localMetaDataColumn.getColumnName();
            String columnType = localMetaDataColumn.getColumnType();
            if (StrUtil.isBlank(columnName)
                    || StrUtil.isBlank(columnType)) {
                throw new BizException(Errors.SysException, "columnName or columnType is null!");
            }
        });

        metaData.setMetaDataColumnList(req.getMetaDataColumnList());
        metaDataService.update(metaData);

        return JsonResponse.success();
    }

    /**
     * 元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    @ApiOperation(value = "元数据操作：上架、下架和删除")
    @PostMapping("/localMetaDataOp")
    public JsonResponse localMetaDataOp(@RequestBody @Validated MetaDataActionReq req) {
        String action = req.getAction();
        switch (action) {
            case "-1"://删除，软删除，status=4
                metaDataService.delete(req.getId(), req.getSign());
                break;
            case "0"://下架，status=3
                metaDataService.down(req.getId(), req.getSign());
                break;
            case "1"://上架，status=2
                metaDataService.up(req.getId(), req.getSign());
                break;
            default:
                throw new ArgumentException();
        }

        return JsonResponse.success();
    }

    /**
     * 获取元数据MetaDataOption
     */
    @ApiOperation(value = "获取元数据MetaDataOption")
    @PostMapping("/getMetaDataOption")
    public JsonResponse<String> getMetaDataOption(@RequestBody @Validated GetMetaDataOptionReq req) {
        String metaDataOption = metaDataService.getMetaDataOption(req.getId());
        return JsonResponse.success(metaDataOption);
    }

    /**
     * 源文件下载
     */
    @ApiOperation(value = "源文件下载")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "文件id", required = true, paramType = "query", example = "1"),
    })
    @GetMapping("/download")
    public void downLoad(HttpServletResponse response, @Validated @NotNull(message = "id不为空") Integer id) {
        metaDataService.downLoad(response, id);
    }

    /**
     * 校验文件名称是否合法
     */
    @ApiOperation(value = "校验元数据名称是否合法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "resourceName", value = "元数据名称", required = true, paramType = "query", example = "filename"),
    })
    @PostMapping("/checkResourceName")
    public JsonResponse checkResourceName(HttpSession session, String resourceName) {
        String currentUserAddress = getCurrentUserAddress(session);
        //判断格式是否对
        if (!NameUtil.isValidName(resourceName)) {
            return JsonResponse.fail(new MetadataResourceNameIllegal());
        }
        //判断是否重复
        boolean exist = metaDataService.isExistResourceName(resourceName, currentUserAddress);
        if (exist) {
            return JsonResponse.fail(new MetadataResourceNameExists());
        }
        return JsonResponse.success();
    }
}
