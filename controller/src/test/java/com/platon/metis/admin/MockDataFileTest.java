package com.platon.metis.admin;

import com.platon.metis.admin.dao.LocalDataFileMapper;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataColumn;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.service.LocalDataService;
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
    private LocalDataService localDataService;

    @Resource
    private LocalDataFileMapper localDataFileMapper;

    @Resource
    LocalOrgMapper localOrgMapper;

    @BeforeAll
    public void init(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);;
    }

    @Test
    public void addDataAuth(){
        int columns = 10;
        for(int i=0; i<1000; i++){
            LocalDataFile dataFile = new LocalDataFile();
            String fileId = StringUtils.leftPad(String.valueOf(i) , 6, "0" );
            dataFile.setFileId(fileId);
            dataFile.setFilePath(fileId + "_path");
            dataFile.setFileName(fileId + "_fileName");
            dataFile.setFileType(LocalDataFile.FileTypeEnum.CSV.getCode());
            dataFile.setColumns(columns);
            dataFile.setHasTitle(true);
            dataFile.setIdentityId(LocalOrgCache.getLocalOrgIdentityId());
            dataFile.setRows(1000);
            dataFile.setSize(1024*1024*32L);
            localDataFileMapper.insert(dataFile);


            LocalMetaData localMetaData = new LocalMetaData();
              localMetaData.setMetaDataId("metadataId_" + i);
            localMetaData.setMetaDataName("metadata_" + i);
            localMetaData.setIndustry(1);
            localMetaData.setStatus(2);
            localMetaData.setFileId(fileId);
            localMetaData.setPublishTime(randomDay());
            localMetaData.setRemarks("metadataId_" + i + "_remarks");
            for(int j=0; j<columns; j++){
                LocalMetaDataColumn column = new LocalMetaDataColumn();
                column.setColumnIdx(j);
                column.setColumnName("column_" + j);
                column.setSize(10);
                column.setVisible(true);
                column.setColumnType("string");
                column.setRemarks("column_" + j + "_desc");
                localMetaData.addLocalMetaDataColumn(column);

            }
            localDataService.addLocalMetaData(localMetaData);
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
