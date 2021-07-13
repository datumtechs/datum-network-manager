package com.platon.rosettanet.admin.controller.resource;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.LocalDataActionReq;
import com.platon.rosettanet.admin.dto.resp.LocalDataPageResp;
import com.platon.rosettanet.admin.service.LocalDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
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



@RestController
@RequestMapping("/api/v1/resource/mydata/")
public class LocalDataController {

    @Resource
    private LocalDataService localDataService;

    /**
     * 展示数据列表，带分页
     */
    @GetMapping("metaDataList")
    public JsonResponse<LocalDataPageResp> page(int pageNum, int pageSize){
        Page<LocalDataFile> localDataFilePage = localDataService.listDataFile(pageNum, pageSize,null);
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }

    /**
     * 根据关键字查询机构自身的元数据列表摘要信息
     */
    @GetMapping("metaDataListByKeyWord")
    public JsonResponse<LocalDataPageResp> metaDataListByKeyWord(int pageNum, int pageSize,String keyword){
        Page<LocalDataFile> localDataFilePage = localDataService.listDataFile(pageNum, pageSize,keyword);
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }


    /**
     * 导入文件，文件上传+新增元数据信息,进行解析并将解析后的数据返回给前端
     */
    @PostMapping("uploadFile")
    public JsonResponse<LocalDataFileDetail> importFile(MultipartFile file,Boolean hasTitle) throws IOException {
        //校验文件
        isValidFile(file);
        LocalDataFileDetail localDataFileDetail = localDataService.uploadFile(file, hasTitle);
        return JsonResponse.success(localDataFileDetail);
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
    }

    /**
     * 数据添加：只是更新数据库信息
     */
    @PostMapping("addMetaData")
    public JsonResponse add(@RequestBody @Validated LocalDataFileDetail req){
        int count = localDataService.add(req);
        if(count <= 0){
            return JsonResponse.fail("添加失败");
        }
        return JsonResponse.success();
    }

    /**
     * 查看数据详情
     */
    @GetMapping("metaDataInfo")
    public JsonResponse<LocalDataFileDetail> detail(@Validated @NotBlank(message = "metaDataId不为空") String metaDataId){
        LocalDataFileDetail detail = localDataService.detail(metaDataId);
        return JsonResponse.success(detail);
    }

    /**
     * 修改数据信息
     */
    @PostMapping("updateMetaData")
    public JsonResponse update(@RequestBody @Validated LocalDataFileDetail req){
        int count = localDataService.update(req);
        if(count <= 0){
            return JsonResponse.fail("更新失败");
        }
        return JsonResponse.success();
    }

    /**
     * 元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    @PostMapping("actionMetaData")
    public JsonResponse actionMetaData(@RequestBody @Validated LocalDataActionReq req){
        String action = req.getAction();
        int count = 0;
        switch (action){
            case "-1"://删除
                //count = localDataService.delete(req.getMetaDataId());
                break;
            case "0"://下架
                //count = localDataService.down(req.getMetaDataId());
                break;
            case "1"://上架
                //count = localDataService.up(req.getMetaDataId());
                break;
            default:
                throw new ApplicationException(StrUtil.format("请输入正确的action：{}",action));
        }
        //调试阶段
        count = 1;
        if(count <= 0){
            JsonResponse.fail("操作失败");
        }
        return JsonResponse.success();
    }

    /**
     * 源文件下载
     */
    @GetMapping("download")
    public void downLoad(HttpServletResponse response, @Validated @NotBlank(message = "metaDataId不为空") String metaDataId){
        localDataService.downLoad(response,metaDataId);
    }

}
