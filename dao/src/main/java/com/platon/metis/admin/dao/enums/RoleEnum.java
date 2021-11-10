package com.platon.metis.admin.dao.enums;

import lombok.Getter;

public enum RoleEnum {

    UNDEFINED(0, "undefined","未定义"),
    OWNER(1, "owner", "任务发起方"),
    POWERSUPPLIER(2, "powerSupplier","算力提供方"),
    DATASUPPLIER(3, "dataSupplier","数据提供方"),
    RECEIVER(4, "receiver", "结果接收方"),
    ALGOSUPPLIER(5, "algoSupplier", "算法提供方");

    RoleEnum(int code, String message, String remark){
        this.code = code;
        this.message = message;
        this.remark = remark;
    }

    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private String remark;


    public static String getMessageByCode(int code){
         for (RoleEnum role : RoleEnum.values()){
             if(role.code == code){
                return role.message;
             }
         }
         return "";
    }


}
