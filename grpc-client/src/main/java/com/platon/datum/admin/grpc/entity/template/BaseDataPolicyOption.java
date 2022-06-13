package com.platon.datum.admin.grpc.entity.template;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @Author liushuyu
 * @Date 2022/6/13 12:11
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
public abstract class BaseDataPolicyOption {

    public String getHash(Object... params) {
        Object[] parameterNameArray = getParameterNameArray();
        Object[] append = ArrayUtil.append(parameterNameArray, params);
        return String.valueOf(Objects.hash(append));
    }

    protected abstract Object[] getParameterNameArray();

}
