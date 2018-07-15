package com.lpy.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipeiyuan on 2018/7/15.
 * 为了方便展示
 */
public class ViewObject {
    private Map<String,Object> objectMap = new HashMap<>();
    public void set(String key , Object value) {
        objectMap.put(key,value);
    }
    public Object get(String key) {
        return objectMap.get(key);
    }
}
