package com.platon.metis.admin.common.exception;

public class CarrierNotConfigured extends BizException {
    public CarrierNotConfigured() {
        super(Errors.CarrierNotConfigured.getCode(), Errors.CarrierNotConfigured.getMessage());
    }
}
