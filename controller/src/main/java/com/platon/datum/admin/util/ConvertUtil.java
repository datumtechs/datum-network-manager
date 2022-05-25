package com.platon.datum.admin.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author houz
 */
public class ConvertUtil {

    public static String convertFileNameToResourceName(String fileName){
        if (StringUtils.isBlank(fileName)){
            return "";
        }

        return  StringUtils.substring(StringUtils.substring(fileName,0, fileName.lastIndexOf(".")),0, 64);
    }
}
