package com.platon.metis.admin.common.annotation;


import com.platon.metis.admin.common.util.AddressChangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class AddressValidation implements ConstraintValidator<CheckAddress, String> {
    @Override
    public void initialize(CheckAddress constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return false;
        } else {
            try {
                return AddressChangeUtil.convert0xAddress(value).toLowerCase().startsWith("0x");
            } catch (Exception e) {
                log.error("钱包地址格式错误, value:{}, 错误信息:{}", value, e.getMessage());
                return false;
            }
        }
    }
}
