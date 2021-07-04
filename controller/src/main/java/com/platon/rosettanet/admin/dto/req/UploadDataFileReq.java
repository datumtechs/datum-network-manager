package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class UploadDataFileReq {
    @NotBlank(message = "是否有表头的标识不能为空")
    private Boolean hasTitle;

    private MultipartFile file;//文件

    @NotBlank(message = "元数据名称不能为空")
    private String metadataName;

    private String remarks;

    private List<LocalMetaDataColumn> localMetaDataColumnList;
}
