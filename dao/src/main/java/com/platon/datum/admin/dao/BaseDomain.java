package com.platon.datum.admin.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BaseDomain extends Object implements Serializable {

    /**
     * 动态字段. 在mybatis文件中可用“dynamicFields.xxx”方式读取动态字段值
     */
    protected Map dynamicFields = new HashMap();


    /**
     * 设置动态字段值.
     *
     * @param fieldName
     *            字段名称
     * @param value
     *            字段值
     */
    public void setField(String fieldName, Object value) {
        // if (dynamicFields == null) {
        // dynamicFields = new HashMap();
        // }
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

    public Map getDynamicFields() {
        if (dynamicFields != null && dynamicFields.size() > 0) {
            Set set = dynamicFields.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                if (dynamicFields.get(key) != null && dynamicFields.get(key).getClass().isArray()) {
                    Object[] objArr = (Object[]) dynamicFields.get(key);
                    if (objArr.length == 1) {
                        dynamicFields.put(key, ((Object[]) dynamicFields.get(key))[0]);
                    }
                }
            }
        }
        return dynamicFields;
    }

    public void setDynamicFields(Map dynamicFields) {
        this.dynamicFields = dynamicFields;
    }
}
