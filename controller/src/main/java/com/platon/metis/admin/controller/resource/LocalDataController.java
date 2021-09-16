package com.platon.metis.admin.controller.resource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.entity.LocalDataFileDetail;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataItem;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.enums.DataAddTypeEnum;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.dto.CommonPageReq;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.*;
import com.platon.metis.admin.dto.resp.*;
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
@RequestMapping("/api/v1/resource/mydata/")
public class LocalDataController {

    @Resource
    private LocalDataService localDataService;

    /**
     * 展示数据列表，带分页
     */
    @ApiOperation(value = "数据列表分页查询")
    @PostMapping("metaDataList")
    public JsonResponse<List<LocalDataPageResp>> page(@RequestBody @Validated CommonPageReq req){
        Page<LocalMetaDataItem> localDataFilePage = localDataService.listDataFile(req.getPageNumber(), req.getPageSize(),null);
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }

    /**
     * 根据关键字查询机构自身的元数据列表摘要信息
     */
    @ApiOperation(value = "数据列表关键字查询")
    @PostMapping("metaDataListByKeyWord")
    public JsonResponse<List<LocalDataPageResp>> metaDataListByKeyWord(@RequestBody @Validated LocalDataMetaDataListByKeyWordReq req){
        Page<LocalMetaDataItem> localDataFilePage = localDataService.listDataFile(req.getPageNumber(), req.getPageSize(),req.getKeyword());
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }


    /**
     * 根据数据id查询数据参与的任务信息列表
     */
    @ApiOperation(value = "数据参与的任务信息列表")
    @PostMapping("queryDataJoinTaskList")
    public JsonResponse<List<TaskDataPageResp>> queryDataJoinTaskList(@RequestBody @Validated LocalDataJoinTaskListReq req){
        Page<Task> localDataJoinTaskPage = localDataService.listDataJoinTask(req.getPageNumber(), req.getPageSize(),req.getMetaDataId(),req.getKeyword());
        List<TaskDataPageResp> taskDataPageList = localDataJoinTaskPage.getResult().stream().map(TaskDataPageResp::convert).collect(Collectors.toList());
        return JsonResponse.page(localDataJoinTaskPage,taskDataPageList);
    }


    /**
     * 导入文件，文件上传+新增元数据信息,进行解析并将解析后的数据返回给前端
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
        LocalDataFileDetail detail = localDataService.uploadFile(file, hasTitle);
        LocalDataImportFileResp resp = LocalDataImportFileResp.from(detail);
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
            throw new ApplicationException("文件名称格式不合法:仅支持中英文与数字输入，最多12个字符");
        }
        //判断文件名称是否重复
        /*boolean exist = localDataService.isExistResourceName(resourceName,null);
        if(exist){
            throw new ApplicationException("文件名称已存在，请修改文件名称前12个字符，确保不会和已存在的文件前12个字符重复！！！");
        }*/
    }

    /**
     * 数据添加：实际只是更新数据库信息，在上一步导入文件的时候已经插入数据
     * 另存为新数据：复制一份数据进行保存
     */
    @ApiOperation(value = "添加数据/另存为新数据")
    @PostMapping("addMetaData")
    public JsonResponse add(@RequestBody @Validated LocalDataAddReq req){
        int count = 0;
        //判断数据添加类型
        if(req.getAddType() != DataAddTypeEnum.ADD.getType() && req.getAddType() != DataAddTypeEnum.ADD_AGAIN.getType()){
           return JsonResponse.fail("数据添加类型不正确，类型为 1：新增数据、2：另存为新数据");
        }
        //判断格式是否对
        if(!NameUtil.isValidName(req.getResourceName())){
            return JsonResponse.fail("文件名称格式不合法:仅支持中英文与数字输入，最多12个字符");
        }
        //判断是否重复
        boolean exist = localDataService.isExistResourceName(req.getResourceName(),req.getMetaDataPKId());
        if(exist){
            return JsonResponse.fail("文件名称已存在，请修改文件名称前12个字符，确保不会和已存在的文件前12个字符重复！！！");
        }
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(req,detail);

        LocalMetaData metaData = new LocalMetaData();
        metaData.setId(req.getMetaDataPKId());
        metaData.setRemarks(req.getRemarks());
        metaData.setIndustry(req.getIndustry());
        metaData.setMetaDataName(req.getResourceName());
        metaData.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());//添加数据状态为created
        metaData.setHasOtherSave(true);//标记为数据另存
        //添加数据/另存为新数据
        if(req.getAddType() == DataAddTypeEnum.ADD.getType()){
            count = localDataService.add(detail, metaData);
        } else {
            count = localDataService.addAgain(detail, metaData);
        }
        if(count <= 0){
            return JsonResponse.fail("添加失败");
        }
        return JsonResponse.success();
    }





    /**
     * 查看数据详情
     */
    @ApiOperation(value = "查看数据详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "文件id",required = true,paramType = "query",example = "1"),
    })
    @GetMapping("metaDataInfo")
    public JsonResponse<LocalDataDetailResp> detail(@Validated @NotNull(message = "id不为空") Integer id){
        LocalDataFileDetail detail = localDataService.detail(id);
        LocalDataDetailResp resp = LocalDataDetailResp.from(detail);
        return JsonResponse.success(resp);
    }

    /**
     * 修改数据信息
     */
    @ApiOperation(value = "修改数据信息")
    @PostMapping("updateMetaData")
    public JsonResponse update(@RequestBody @Validated LocalDataUpdateReq req){
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(req,detail);
        detail.setId(null);

        LocalMetaData metaData = new LocalMetaData();
        metaData.setRemarks(req.getRemarks());
        metaData.setIndustry(req.getIndustry());
        metaData.setId(req.getId());
        int count = localDataService.update(detail, metaData);
        if(count <= 0){
            return JsonResponse.fail("更新失败");
        }
        return JsonResponse.success();
    }

    /**
     * 元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    @ApiOperation(value = "元数据上下架和删除")
    @PostMapping("actionMetaData")
    public JsonResponse actionMetaData(@RequestBody @Validated LocalDataActionReq req){
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
                throw new ApplicationException(StrUtil.format("请输入正确的action[-1: 删除; 0: 下架; 1: 上架]：{}",action));
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
    @ApiOperation(value = "校验文件名称是否合法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "resourceName",value = "文件名称",required = true,paramType = "query",example = "filename"),
            @ApiImplicitParam(name = "id",value = "文件的id",required = true,paramType = "query",example = "1"),
    })
    @PostMapping("checkResourceName")
    public JsonResponse<LocalDataCheckResourceNameResp> checkResourceName(String resourceName,Integer id){
        LocalDataCheckResourceNameResp resp = new LocalDataCheckResourceNameResp();
        //判断格式是否对
        if(!NameUtil.isValidName(resourceName)){
            return JsonResponse.success(resp);
        }
        //判断是否重复
        boolean exist = localDataService.isExistResourceName(resourceName,id);
        if(exist){
            return JsonResponse.success(resp);
        }
        resp.setStatus(ControllerConstants.STATUS_AVAILABLE);
        return JsonResponse.success(resp);
    }
}
