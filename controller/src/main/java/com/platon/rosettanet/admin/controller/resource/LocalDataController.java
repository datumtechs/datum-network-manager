package com.platon.rosettanet.admin.controller.resource;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.LocalDataImportFileReq;
import com.platon.rosettanet.admin.dto.resp.LocalDataPageResp;
import com.platon.rosettanet.admin.service.LocalDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
        Page<LocalDataFile> localDataFilePage = localDataService.listDataFile(pageNum, pageSize);
        List<LocalDataPageResp> respList = localDataFilePage.getResult().stream()
                .map(LocalDataPageResp::from)
                .collect(Collectors.toList());
        return JsonResponse.page(localDataFilePage,respList);
    }

    /**
     * 导入文件，进行解析并将解析后的数据返回给前端
     */
    public JsonResponse importFile(@RequestBody @Validated LocalDataImportFileReq req){
        MultipartFile file = req.getFile();
        Boolean hasTitle = req.getHasTitle();
        localDataService.uploadFile(file,hasTitle);


        return JsonResponse.success();
    }

    /**
     * 数据添加：文件上传+新增元数据信息
     */
    public JsonResponse add(@RequestBody @Validated LocalDataImportFileReq req){
        localDataService.uploadFile(req.getFile(),req.getHasTitle());
        return JsonResponse.success();
    }

    /**
     * 查看数据详情
     */
    public void detail(){

    }

    /**
     * 修改数据信息
     */
    public void update(){

    }

    /**
     * 删除源文件，当前版本直接真删除，包括元数据
     */
    public void delete(){

    }

    /**
     * 源文件下载
     */
    public void downLoad(){


    }

    /**
     * 文件元数据上架
     */
    public void up(){

    }

    /**
     * 文件元数据下架
     */
    public void down(){

    }
}
