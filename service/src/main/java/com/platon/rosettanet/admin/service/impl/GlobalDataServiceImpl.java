package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.dao.GlobalDataFileMapper;
import com.platon.rosettanet.admin.dao.GlobalDataFileMapper;
import com.platon.rosettanet.admin.dao.GlobalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.LocalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.service.GlobalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return globalDataFilePage;
    }

    @Override
    public GlobalDataFileDetail detail(String metaDataId) {
        GlobalDataFile globalDataFile = globalDataFileMapper.selectByMetaDataId(metaDataId);

        List<GlobalMetaDataColumn> columnList = globalMetaDataColumnMapper.selectByMetaDataId(metaDataId);
        GlobalDataFileDetail detail = new GlobalDataFileDetail();
        BeanUtils.copyProperties(globalDataFile,detail);
        detail.setMetaDataColumnList(columnList);
        return detail;
    }
}
