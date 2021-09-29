package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.dao.GlobalDataFileMapper;
import com.platon.metis.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.metis.admin.dao.entity.GlobalDataFile;
import com.platon.metis.admin.dao.entity.GlobalMetaDataColumn;
import com.platon.metis.admin.service.GlobalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/12 2:08
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class GlobalDataServiceImpl implements GlobalDataService {


    @Resource
    private GlobalDataFileMapper globalDataFileMapper;
    @Resource
    private GlobalMetaDataColumnMapper globalMetaDataColumnMapper;


    @Override
    public Page<GlobalDataFile> listDataFile(int pageNum, int pageSize, String keyword) {
        Page<GlobalDataFile> globalDataFilePage = PageHelper.startPage(pageNum, pageSize);
        globalDataFileMapper.listDataFile(keyword);
        List<GlobalDataFile> detailList = globalDataFilePage.stream()
                .map(dataFile -> {
                    List<GlobalMetaDataColumn> columnList = globalMetaDataColumnMapper.selectByFileId(dataFile.getFileId());
                    dataFile.setMetaDataColumnList(columnList);
                    return dataFile;
                })
                .collect(Collectors.toList());
        globalDataFilePage.clear();
        //重新赋值
        globalDataFilePage.addAll(detailList);
        return globalDataFilePage;
    }

    @Override
    public GlobalDataFile findGlobalDataFile(Integer id) {
        GlobalDataFile globalDataFile = globalDataFileMapper.selectById(id);
        List<GlobalMetaDataColumn> columnList = globalMetaDataColumnMapper.selectByFileId(globalDataFile.getFileId());
        globalDataFile.setMetaDataColumnList(columnList);
        return globalDataFile;
    }
}
