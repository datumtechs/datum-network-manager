package com.platon.datum.admin.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExportFileUtil {

      /**
     * 导出Csv
     * @param filename 文件名，不带后缀
     * @param content 文件字节
     * @param response
     */
    public static void exportCsv(String filename, byte[] content, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(content);
            outputStream.flush();
        }
    }
}
