package com.platon.rosettanet.admin.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LocalDataImportFileReq {
    @NotBlank(message = "是否有表头的标识不能为空")
    private Boolean hasTitle;

    private MultipartFile file;//文件
}
