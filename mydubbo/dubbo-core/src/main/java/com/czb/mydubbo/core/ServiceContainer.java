package com.czb.mydubbo.core;

import java.util.HashMap;

/**
 * 模拟spring容器
 */
public class ServiceContainer {

    private  static HashMap<String ,Object> map = new HashMap();
    public static void set(String serviceName,Object object){
        map.put(serviceName,object);
    }

    public static Object get(String name){
        return map.get(name);
    }
}
