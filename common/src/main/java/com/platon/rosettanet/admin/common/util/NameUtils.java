package com.platon.rosettanet.admin.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author liushuyu
 * @Date 2021/7/5 10:18
 * @Version
 * @Desc
 */
public class NameUtils {

    public static final String NAME_REG_STR = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{0,12}$";

    /*“机构识别名称”与后续的“计算节点名称”、“数据节点名称”、“源文件名称”命名规则：
        1. 设置之后无法修改；
        2. 命名规则相同，仅支持中英文与数字输入；
        3.最多12个字符。
    */
    public static boolean isValidName(String name){
        Pattern pattern = Pattern.compile(NAME_REG_STR);
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isValidName("123456789abc"));
    }
}
