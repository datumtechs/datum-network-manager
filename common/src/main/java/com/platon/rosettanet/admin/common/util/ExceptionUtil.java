package com.platon.rosettanet.admin.common.util;

/**
 * @Author liushuyu
 * @Date 2021/7/14 11:41
 * @Version
 * @Desc
 */

/**
 * 异常处理工具类
 */

public class ExceptionUtil extends cn.hutool.core.exceptions.ExceptionUtil{


    /**
     * 判断是否是因为指定异常直接或者间接导致的
     * @param error 当前抛出的异常
     * @param exceptionClassArray 指定异常数组
     * @return
     */
    public static boolean causeBy(Throwable error,Class[] exceptionClassArray){
        if (exceptionClassArray.length <= 0) {
            return false;
        }

        for (int i = 0; i < exceptionClassArray.length; i++) {
            Class exceptionClass = exceptionClassArray[i];
            if(exceptionClass.isInstance(error)){
                return true;
            }

            Throwable temp = error;
            while(temp.getCause() != null){
                if(exceptionClass.isInstance(temp.getCause())){
                    return true;
                }
                temp = temp.getCause();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /**
         * 直接抛出ClassNotFoundException
         */
        try {
            ClassNotFoundException classNotFoundException = new ClassNotFoundException("");
            throw classNotFoundException;
        } catch (Exception exception){
            System.out.println(causeBy(exception,new Class[]{ClassNotFoundException.class}));
        }

        /**
         * 间接抛出ClassNotFoundException
         */
        try {
            ClassNotFoundException classNotFoundException = new ClassNotFoundException("");
            RuntimeException exception = new RuntimeException(classNotFoundException);
            throw exception;
        } catch (Exception exception){
            System.out.println(causeBy(exception,new Class[]{ClassNotFoundException.class}));
        }
    }
}
