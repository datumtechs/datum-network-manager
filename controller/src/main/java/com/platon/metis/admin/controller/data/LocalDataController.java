package com.platon.metis.admin.controller.data;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.LocalDataFileColumnMapper;
import com.platon.metis.admin.dao.LocalDataFileMapper;
import com.platon.metis.admin.dao.LocalMetaDataColumnMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataColumn;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.enums.DataAddTypeEnum;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.dto.CommonPageReq;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.*;
import com.platon.metis.admin.dto.resp.LocalDataCheckResourceNameResp;
import com.platon.metis.admin.dto.resp.LocalDataDetailResp;
import com.platon.metis.admin.dto.resp.LocalDataImportFileResp;
import com.platon.metis.admin.dto.resp.LocalDataPageResp;
import com.platon.metis.admin.service.LocalDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/8 23:14
 * @Version
 * @Desc 我的数据
 */


@Api(tags = "我的数据")
@RestController
@RequestMapping("/api/v1/data/")
public class LocalDataController {

    @Resource
    private LocalDataService localDataService;

    @Resource
    private LocalDataFileMapper localDataFileMapper;
    @Resource
    private LocalDataFileColumnMapper localDataFileColumnMapper;

    @Resource
    private LocalMetaDataMapper localMetaDataMapper;
    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;
    /**
     * 展示数据列表，带分页
     */
    @ApiOperation(value = "数据列表分页查询")
    @PostMapping("listLocalMetaData")
    public JsonResponse<List<LocalDataPageResp>> listMetaData(@RequestBody @Validated CommonPageReq req){
        Page<LocalMetaData> localDataFilePage = localDataService.listMetaData(req.getPageNumber(), req.getPageSize(),null);
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }

    /**
     * 根据关键字查询机构自身的元数据列表摘要信息
     */
    @ApiOperation(value = "数据列表关键字查询")
    @PostMapping("listLocalMetaDataByKeyword")
    public JsonResponse<List<LocalDataPageResp>> listLocalMetaDataByKeyword(@RequestBody @Validated LocalDataMetaDataListByKeyWordReq req){
        Page<LocalMetaData> localDataFilePage = localDataService.listMetaData(req.getPageNumber(), req.getPageSize(),req.getKeyword());
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }


    /**
     * 根据数据id查询数据参与的任务信息列表
     */
    @ApiOperation(value = "数据参与的任务信息列表")
    @PostMapping("listTaskByMetaDataId")
    public JsonResponse<List<Task>> listTaskByMetaDataId(@RequestBody @Validated LocalDataJoinTaskListReq req){
        Page<Task> localDataJoinTaskPage = localDataService.listDataJoinTask(req.getPageNumber(), req.getPageSize(),req.getMetaDataId(),req.getKeyword());
        //List<TaskDataPageResp> taskDataPageList = localDataJoinTaskPage.getResult().stream().map(TaskDataPageResp::convert).collect(Collectors.toList());
        return JsonResponse.page(localDataJoinTaskPage,localDataJoinTaskPage);
    }


    /**
     * 导入文件，并解析文件格式和内容，把文件信息存入local_data_file，把文件列信息返回给前端。
     * 注意：此时正常逻辑应该把列信息存入local_data_file_column，但是这里并没有。
     * 为了简化后续操作的数据查询，只是在添加元数据时，把全量的列信息存入local_meta_data_column表中。
     * 因此，local_data_file_column表其实没有用了。
     */
    @ApiOperation(value = "导入文件")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "hasTitle",value = "是否包含表头",required = true,paramType = "query",example = "true"),
    })
    @PostMapping("uploadFile")
    public JsonResponse<LocalDataImportFileResp> importFile(MultipartFile file,
                                                        @Validated @NotNull(message = "hasTitle不为空")Boolean hasTitle) throws IOException {
        //校验文件
        isValidFile(file);
        LocalDataFile localDataFile = localDataService.uploadFile(file, hasTitle);
        LocalDataImportFileResp resp = LocalDataImportFileResp.from(localDataFile);
        return JsonResponse.success(resp);
    }

    private void isValidFile(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new ApplicationException("文件大小为空");
        }

        //如果是csv文件
        CsvReader reader = CsvUtil.getReader();
        InputStreamReader isr = new InputStreamReader(file.getInputStream());
        CsvData csvData = reader.read(isr);
        //### 1.先确定确定有多少行
        int rowCount = csvData.getRowCount();//TODO 如果行数太多了，超过Integer.max会报错
        if(rowCount <= 0){
            throw new ApplicationException("CSV文件为空文件");
        }

        //校验文件名
        String originalFilename = file.getOriginalFilename();
        String resourceName = StrUtil.sub(FileUtil.getPrefix(originalFilename),0,12);
        if(!NameUtil.isValidName(resourceName)){
            throw new ApplicationException("元数据资源名称错误：仅支持中英文与数字输入，最多12个字符");
        }
        //判断文件名称是否重复
        /*boolean exist = localDataService.isExistResourceName(resourceName,null);
        if(exist){
            throw new ApplicationException("文件名称已存在，请修改文件名称前12个字符，确保不会和已存在的文件前12个字符重复！！！");
        }*/
    }

    /**
     * 元数据添加：
     * 在添加元数据时，需要把元数据信息存入local_meta_data表，并把全量的列信息存入local_meta_data_column表中（带visible）。
     */
    @ApiOperation(value = "添加数据/另存为新数据")
    @PostMapping("addLocalMetaData")
    public JsonResponse addLocalMetaData(@RequestBody @Validated AddLocalMetaDataReq req){
        int count = 0;
        //判断数据添加类型
        if(req.getAddType() != DataAddTypeEnum.ADD.getType() && req.getAddType() != DataAddTypeEnum.ADD_AGAIN.getType()){
           return JsonResponse.fail("数据添加类型不正确，类型为 1：新增数据、2：另存为新数据");
        }
        //判断格式是否对
        if(!NameUtil.isValidName(req.getResourceName())){
            return JsonResponse.fail("元数据资源名称错误:仅支持中英文与数字输入，最多12个字符");
        }
        //判断是否重复
        boolean exist = localDataService.isExistResourceName(req.getResourceName(),req.getFileId());
        if(exist){
            return JsonResponse.fail("元数据资源名称已存在！！！");
        }
        LocalDataFile localDataFile = new LocalDataFile();
        BeanUtils.copyProperties(req, localDataFile);

        LocalMetaData localMetaData = new LocalMetaData();
        localMetaData.setFileId(req.getFileId());
        localMetaData.setRemarks(req.getRemarks());
        localMetaData.setIndustry(req.getIndustry());
        localMetaData.setMetaDataName(req.getResourceName());
        localMetaData.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());//添加数据状态为created

        localMetaData.setLocalMetaDataColumnList(req.getLocalMetaDataColumnList());

        count = localDataService.addLocalMetaData(localMetaData);

        if(count <= 0){
            return JsonResponse.fail("添加失败");
        }
        return JsonResponse.success();
    }

    /*private List<LocalMetaDataColumn> visibleLocalDataColumns(List<LocalDataFileColumn> localDataFileColumnList){
        List<LocalMetaDataColumn> localMetaDataColumnList = new ArrayList<>();
        for(LocalDataFileColumn localDataFileColumn : localDataFileColumnList){
            if(localDataFileColumn.isVisible()){
                LocalMetaDataColumn localMetaDataColumn = new LocalMetaDataColumn();
                localMetaDataColumn.setColumnIdx(localDataFileColumn.getColumnIdx());
                localMetaDataColumn.setColumnName(localDataFileColumn.getColumnName());
                localMetaDataColumn.setColumnType(localDataFileColumn.getColumnType());
                localMetaDataColumn.setSize(localDataFileColumn.getSize());
                localMetaDataColumn.setRemarks(localDataFileColumn.getRemarks());
                localMetaDataColumnList.add(localMetaDataColumn);
            }
        }
        return localMetaDataColumnList;
    }*/



    /**
     * 查看元数据详情
     */
    @ApiOperation(value = "查看元数据详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "元数据db key",required = true,paramType = "query",example = "1"),
    })
    @GetMapping("localMetaDataInfo")
    public JsonResponse<LocalMetaData> detail(@Validated @NotNull(message = "id不为空") Integer id){
        //查询localMetaData，并查询出taskCount放入动态字段
        LocalMetaData localMetaData = localMetaDataMapper.findWithTaskCount(id);
        if(localMetaData==null){
            throw new ApplicationException("metadata not found");
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
    @PostMapping("updateLocalMetaData")
    public JsonResponse update(@RequestBody @Validated LocalDataUpdateReq req){
        LocalMetaData localMetaData = new LocalMetaData();
        localMetaData.setRemarks(req.getRemarks());
        localMetaData.setIndustry(req.getIndustry());
        localMetaData.setId(req.getId());
        localMetaData.setLocalMetaDataColumnList(req.getLocalMetaDataColumnList());

       /* List<LocalMetaDataColumn> localMetaDataColumnList = visibleLocalDataColumns(req.getLocalDataFileColumnList());

        if(localMetaDataColumnList.size()>0){
            localMetaData.setLocalMetaDataColumnList(localMetaDataColumnList);
        }*/

        int count = localDataService.update(localMetaData);

        if(count <= 0){
            return JsonResponse.fail("更新失败");
        }
        return JsonResponse.success();
    }

    /**
     * 元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    @ApiOperation(value = "元数据操作：上架、下架和删除")
    @PostMapping("localMetaDataOp")
    public JsonResponse localMetaDataOp(@RequestBody @Validated LocalDataActionReq req){
        String action = req.getAction();
        int count = 0;
        switch (action){
            case "-1"://删除
                count = localDataService.delete(req.getId());
                break;
            case "0"://下架
                count = localDataService.down(req.getId());
                break;
            case "1"://上架
                count = localDataService.up(req.getId());
                break;
            default:
                throw new ApplicationException(StrUtil.format("请输入正确的操作[-1: 删除; 0: 下架; 1: 上架]：{}",action));
        }
        if(count <= 0){
            JsonResponse.fail("操作失败");
        }
        return JsonResponse.success();
    }

    /**
     * 源文件下载
     */
    @ApiOperation(value = "源文件下载")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "文件id",required = true,paramType = "query",example = "1"),
    })
    @GetMapping("download")
    public void downLoad(HttpServletResponse response, @Validated @NotNull(message = "id不为空") Integer id){
        localDataService.downLoad(response,id);
    }

    /**
     * 校验文件名称是否合法
     */
    @ApiOperation(value = "校验元数据资源名称是否合法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "resourceName",value = "元数据资源名称",required = true,paramType = "query",example = "filename"),
            @ApiImplicitParam(name = "fileId",value = "上传数据文件后产生的唯一文件Id",required = true,paramType = "query",example = "1"),
    })
    @PostMapping("checkResourceName")
    public JsonResponse<LocalDataCheckResourceNameResp> checkResourceName(String resourceName, String fileId){
        LocalDataCheckResourceNameResp resp = new LocalDataCheckResourceNameResp();
        //判断格式是否对
        if(!NameUtil.isValidName(resourceName)){
            return JsonResponse.success(resp);
        }
        //判断是否重复
        boolean exist = localDataService.isExistResourceName(resourceName, fileId);
        if(exist){
            return JsonResponse.success(resp);
        }
        resp.setStatus(ControllerConstants.STATUS_AVAILABLE);
        return JsonResponse.success(resp);
    }
}