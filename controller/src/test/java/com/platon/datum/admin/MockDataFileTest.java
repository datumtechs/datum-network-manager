package com.platon.datum.admin;

import com.platon.datum.admin.dao.DataFileMapper;
import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataFile;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.dao.entity.MetaDataColumn;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional //这个有看需要，测试方法如果要作为一个整体事务，则加上
@Rollback(false) // 默认值：true, UT默认都会回滚数据库，不会增加新数据
@Slf4j
public class MockDataFileTest {
    @Resource
    private MetaDataService metaDataService;

    @Resource
    private DataFileMapper dataFileMapper;

    @Resource
    OrgMapper orgMapper;

    @BeforeAll
    public void init(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);;
    }

    @Test
    public void addDataAuth(){
        int columns = 10;
        for(int i=0; i<1000; i++){
            DataFile dataFile = new DataFile();
            String fileId = StringUtils.leftPad(String.valueOf(i) , 6, "0" );
            dataFile.setFileId(fileId);
            dataFile.setFilePath(fileId + "_path");
            dataFile.setFileName(fileId + "_fileName");
            dataFile.setFileType(DataFile.FileTypeEnum.CSV.getCode());
            dataFile.setColumns(columns);
            dataFile.setHasTitle(true);
            dataFile.setIdentityId(OrgCache.getLocalOrgIdentityId());
            dataFile.setRows(1000);
            dataFile.setSize(1024*1024*32L);
            dataFileMapper.insert(dataFile);


            MetaData metaData = new MetaData();
              metaData.setMetaDataId("metadataId_" + i);
            metaData.setMetaDataName("metadata_" + i);
            metaData.setIndustry(1);
            metaData.setStatus(2);
            metaData.setFileId(fileId);
            metaData.setPublishTime(randomDay());
            metaData.setDesc("metadataId_" + i + "_remarks");
            for(int j=0; j<columns; j++){
                MetaDataColumn column = new MetaDataColumn();
                column.setColumnIdx(j);
                column.setColumnName("column_" + j);
                column.setSize(10);
                column.setVisible(true);
                column.setColumnType("string");
                column.setRemarks("column_" + j + "_desc");
                metaData.addLocalMetaDataColumn(column);

            }
            metaDataService.addLocalMetaData(metaData);
        }
    }

    private LocalDateTime randomDay(){
        int gaps = 360;
        LocalDateTime start = LocalDateTime.now(ZoneOffset.UTC).minusDays(gaps);
        Duration duration = Duration.between(start, LocalDateTime.now(ZoneOffset.UTC));
        long days = duration.toDays(); //相差的天数
        int random = RandomUtils.nextInt(0, (int)days);
        return start.plusDays(random);
    }
}
