package com.platon.datum.admin.controller.data;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.*;
import com.platon.datum.admin.common.util.NameUtil;
import com.platon.datum.admin.controller.BaseController;
import com.platon.datum.admin.dao.LocalDataFileMapper;
import com.platon.datum.admin.dao.LocalMetaDataColumnMapper;
import com.platon.datum.admin.dao.LocalMetaDataMapper;
import com.platon.datum.admin.dao.cache.LocalOrgCache;
import com.platon.datum.admin.dao.entity.LocalDataFile;
import com.platon.datum.admin.dao.entity.LocalMetaData;
import com.platon.datum.admin.dao.entity.LocalMetaDataColumn;
import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dao.enums.DataAddTypeEnum;
import com.platon.datum.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.LocalDataDetailResp;
import com.platon.datum.admin.dto.resp.LocalDataImportFileResp;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.service.LocalDataService;
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
public class LocalDataController extends BaseController {

    @Resource
    private LocalDataService localDataService;

    @Resource
    private TaskService taskService;

    @Resource
    private LocalDataFileMapper localDataFileMapper;

    @Resource
    private LocalMetaDataMapper localMetaDataMapper;
    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;

//    /**
//     * 展示数据列表，带分页
//     */
//    @ApiOperation(value = "数据列表分页查询")
//    @PostMapping("/listLocalMetaData")
//    public JsonResponse<List<LocalMetaData>> listMetaData(@RequestBody @Validated LocalDataListMetaDataReq req, HttpSession session) {
//        String userAddress = getCurrentUserAddress(session);
//        Page<LocalMetaData> localMetaDataPage = localDataService.listMetaData(req.getPageNumber(), req.getPageSize(), null, userAddress,req.getStatus());
//        return JsonResponse.page(localMetaDataPage);
//    }

    /**
     * 根据关键字查询钱包自身的元数据列表摘要信息
     */
    @ApiOperation(value = "数据列表关键字查询")
    @PostMapping("/listLocalMetaDataByKeyword")
    public JsonResponse<List<LocalMetaData>> listLocalMetaDataByKeyword(@RequestBody @Validated LocalDataMetaDataListByKeyWordReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        Page<LocalMetaData> localMetaDataPage = localDataService.listMetaData(req.getPageNumber(), req.getPageSize(), req.getKeyword(), userAddress, req.getStatus());
        return JsonResponse.page(localMetaDataPage);
    }

    /**
     * 根据关键字查询钱包自身的元数据列表摘要信息，状态为未绑定凭证Id的数据
     */
    @ApiOperation(value = "未绑定凭证Id数据列表关键字查询")
    @PostMapping("/listUnBindLocalMetaDataByKeyword")
    public JsonResponse<List<LocalMetaData>> listUnBindLocalMetaDataByKeyword(@RequestBody @Validated LocalDataUnBindMetaDataListByKeyWordReq req, HttpSession session) {
        String userAddress = getCurrentUserAddress(session);
        List<LocalMetaData> localMetaDataPage = localDataService.listUnBindMetaData(req.getKeyword(), userAddress);
        return JsonResponse.success(localMetaDataPage);
    }


    /**
     * 根据数据id查询数据参与的任务信息列表
     */
    @ApiOperation(value = "数据参与的任务信息列表", notes = "返回数据的每个task中，包含如下的动态字段：</br>taskSponsor, powerProvider, dataProvider, resultConsumer, algoProvider </br>当这些字段值等于1时表示本组织在任务中的相应角色")
    @PostMapping("/listTaskByMetaDataId")
    public JsonResponse<List<Task>> listTaskByMetaDataId(@RequestBody @Validated LocalDataJoinTaskListReq req) {
        Page<Task> localDataJoinTaskPage = taskService.listTaskByIdentityIdAndMetaDataIdWithRole(LocalOrgCache.getLocalOrgIdentityId(), req.getMetaDataId(), req.getPageNumber(), req.getPageSize());
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
    public JsonResponse<LocalDataImportFileResp> importFile(MultipartFile file,
                                                            @Validated @NotNull(message = "hasTitle不为空") Boolean hasTitle) {
        //校验文件
        LocalDataFile localDataFile = localDataService.uploadFile(file, hasTitle);
        LocalDataImportFileResp resp = LocalDataImportFileResp.from(localDataFile);
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
        boolean exist = localDataService.isExistResourceName(req.getResourceName(), userAddress);
        if (exist) {
            log.error("AddLocalMetaDataReq.resourceName error:{}", req.getResourceName());
            throw new MetadataResourceNameExists();
        }

        LocalDataFile localDataFile = new LocalDataFile();
        BeanUtils.copyProperties(req, localDataFile);

        LocalMetaData localMetaData = new LocalMetaData();
        localMetaData.setFileId(req.getFileId());
        localMetaData.setRemarks(req.getRemarks());
        localMetaData.setIndustry(req.getIndustry());
        localMetaData.setMetaDataName(req.getResourceName());
        localMetaData.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());//添加数据状态为created
        localMetaData.setOwner(userAddress);
        localMetaData.setMetaDataType(CarrierEnum.MetadataType.MetadataType_DataFile_VALUE);

        localMetaData.setLocalMetaDataColumnList(req.getLocalMetaDataColumnList());

        localDataService.addLocalMetaData(localMetaData);

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
    public JsonResponse<LocalMetaData> detail(@Validated @NotNull(message = "id不为空") Integer id) {
        //查询localMetaData，并查询出taskCount放入动态字段
        LocalMetaData localMetaData = localMetaDataMapper.findWithTaskCount(id);
        if (localMetaData == null) {
            throw new ObjectNotFound();
        }

        //查询元数据的localMetaDataColumnList
        List<LocalMetaDataColumn> localMetaDataColumnList = localMetaDataColumnMapper.selectByLocalMetaDataDbId(id);
        localMetaData.setLocalMetaDataColumnList(localMetaDataColumnList);

        //查询元数据对应localDataFile以及localDataFileColumnList，并把localDataFileColumnList中属于localMetaDataColumnList的字段做标识
        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());
        /*List<LocalDataFileColumn> localDataFileColumnList = localDataFileColumnMapper.listByFileId(localMetaData.getFileId());
        if(!CollectionUtils.isEmpty(localDataFileColumnList)) {
            localDataFile.setLocalDataFileColumnList(localDataFileColumnList);
            for (LocalDataFileColumn localDataFileColumn : localDataFileColumnList) {
                for (LocalMetaDataColumn localMetaDataColumn : localMetaDataColumnList) {
                    if (localDataFileColumn.getColumnIdx().intValue() == localMetaDataColumn.getColumnIdx().intValue()) {
                        localDataFileColumn.setVisible(true);
                        localDataFileColumn.setRemarks(localMetaDataColumn.getRemarks());
                        break;
                    }
                }
            }
        }*/

        LocalDataDetailResp resp = LocalDataDetailResp.from(localDataFile, localMetaData);
        return JsonResponse.success(resp);
    }

    /**
     * 修改数据信息
     */
    @ApiOperation(value = "修改元数据信息")
    @PostMapping("/updateLocalMetaData")
    public JsonResponse update(@RequestBody @Validated LocalDataUpdateReq req) {
        LocalMetaData localMetaData = new LocalMetaData();
        localMetaData.setRemarks(req.getRemarks());
        localMetaData.setIndustry(req.getIndustry());
        localMetaData.setId(req.getId());

        //校验列名和类型不可为空
        List<LocalMetaDataColumn> localMetaDataColumnList = req.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            String columnName = localMetaDataColumn.getColumnName();
            String columnType = localMetaDataColumn.getColumnType();
            if (StrUtil.isBlank(columnName)
                    || StrUtil.isBlank(columnType)) {
                throw new BizException(Errors.SysException, "columnName or columnType is null!");
            }
        });

        localMetaData.setLocalMetaDataColumnList(req.getLocalMetaDataColumnList());
        localDataService.update(localMetaData);

        return JsonResponse.success();
    }

    /**
     * 元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    @ApiOperation(value = "元数据操作：上架、下架和删除")
    @PostMapping("/localMetaDataOp")
    public JsonResponse localMetaDataOp(@RequestBody @Validated LocalDataActionReq req) {
        String action = req.getAction();
        switch (action) {
            case "-1"://删除，软删除，status=4
                localDataService.delete(req.getId());
                break;
            case "0"://下架，status=3
                localDataService.down(req.getId());
                break;
            case "1"://上架，status=2
                localDataService.up(req.getId());
                break;
            default:
                throw new ArgumentException();
        }

        return JsonResponse.success();
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
        localDataService.downLoad(response, id);
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
        boolean exist = localDataService.isExistResourceName(resourceName, currentUserAddress);
        if (exist) {
            return JsonResponse.fail(new MetadataResourceNameExists());
        }
        return JsonResponse.success();
    }
}
