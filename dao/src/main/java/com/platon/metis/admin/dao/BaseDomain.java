package com.platon.metis.admin.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseDomain implements Serializable  {
    /**
     * 动态字段. 在mybatis文件中可用“dynamicFields.xxx”方式读取动态字段值
     */
    protected Map<String, Object> dynamicFields = new HashMap<>();

    /**
     * 设置动态字段值.
     *
     * @param fieldName
     *            字段名称
     * @param value
     *            字段值
     */
    public void setField(String fieldName, Object value) {
        dynamicFields.put(fieldName, value);
    }


    /**
     * 返回动态字段值.
     *
     * @param fieldName
     *            字段名称
     * @return 对象
     */
    public Object getField(String fieldName) {
        if (dynamicFields == null) {
            return null;
        }
        return getDynamicFields().get(fieldName);
    }

    public Map<String, Object> getDynamicFields() {
        if (dynamicFields != null && dynamicFields.size() > 0) {
            Set<String> set = dynamicFields.keySet();
            for (String key : set) {
                if (dynamicFields.get(key) != null && dynamicFields.get(key).getClass().isArray()) {
                    Object[] objArr = (Object[]) dynamicFields.get(key);
                    if (objArr.length == 1) {
                        dynamicFields.put(key, ((Object[])dynamicFields.get(key))[0]);
                    }
                }
            }
        }
        return dynamicFields;
    }

    public void setDynamicFields(Map<String, ?> dynamicFields) {
        this.dynamicFields = (Map<String, Object>)dynamicFields;
    }
}
