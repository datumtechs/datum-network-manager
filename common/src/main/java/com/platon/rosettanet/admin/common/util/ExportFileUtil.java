package com.platon.rosettanet.admin.common.util;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class ExportFileUtil {

    /**
     * 导出Csv
     * @param filename 文件名，不带后缀
     * @param list map封装的数据list
     * @param response
     */
    public static void exportCsv(String filename, List<String[]> list, HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        response.setContentType("application/octet-stream");
        try {
            response.setCharacterEncoding("UTF-8");
            CsvWriter writer = CsvUtil.getWriter(response.getWriter());
            writer.write(list);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new ApplicationException("Excel导出失败",e);
        }
    }

    /**
     * 导出Csv
     * @param filename 文件名，不带后缀
     * @param content 文件字节
     * @param response
     */
    public static void exportCsv(String filename, byte[] content, HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        response.setContentType("application/octet-stream");
        try {
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(content);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new ApplicationException("文件导出失败",e);
        }
    }
}
