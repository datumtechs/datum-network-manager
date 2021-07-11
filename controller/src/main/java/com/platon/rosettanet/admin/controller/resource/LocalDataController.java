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
import com.platon.rosettanet.admin.dto.req.LocalDataDetailReq;
import com.platon.rosettanet.admin.dto.req.LocalDataDownLoadReq;
import com.platon.rosettanet.admin.dto.req.LocalDataImportFileReq;
import com.platon.rosettanet.admin.dto.resp.LocalDataPageResp;
import com.platon.rosettanet.admin.service.LocalDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
public class LocalDataController {

    @Resource
    private LocalDataService localDataService;

    /**
     * 展示数据列表，带分页
     */
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
    public JsonResponse<LocalDataFileDetail> importFile(@RequestBody @Validated LocalDataImportFileReq req) throws IOException {
        MultipartFile file = req.getFile();
        Boolean hasTitle = req.getHasTitle();
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
    public JsonResponse<LocalDataFileDetail> detail(@RequestBody @Validated LocalDataDetailReq req){
        LocalDataFileDetail detail = localDataService.detail(req.getMetaDataId());
        return JsonResponse.success(detail);
    }

    /**
     * 修改数据信息
     */
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
    @PostMapping("/actionMetaData")
    public JsonResponse actionMetaData(@RequestBody @Validated LocalDataActionReq req){
        int action = req.getAction();
        int count = 0;
        switch (action){
            case -1://删除
                count = localDataService.delete(req.getMetaDataId());
                break;
            case 0://下架
                count = localDataService.down(req.getMetaDataId());
                break;
            case 1://上架
                count = localDataService.up(req.getMetaDataId());
                break;
            default:
                throw new ApplicationException(StrUtil.format("请输入正确的action：{}",action));
        }

        if(count <= 0){
            JsonResponse.fail("操作失败");
        }
        return JsonResponse.success();
    }

    /**
     * 源文件下载
     */
    public void downLoad(HttpServletResponse response, @RequestBody @Validated LocalDataDownLoadReq req){
        localDataService.downLoad(response,req.getMetaDataId());
    }

}
