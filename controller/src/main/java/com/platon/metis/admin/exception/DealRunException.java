package com.platon.metis.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houz
 * 统一处理异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealRunException extends RuntimeException {

    /** 异常码 */
    private String code;

    /** 异常信息 */
    private String message;

    public DealRunException(String message){
        super(message);
    }


}
