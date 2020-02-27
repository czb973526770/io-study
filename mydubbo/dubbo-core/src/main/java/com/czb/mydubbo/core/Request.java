package com.czb.mydubbo.core;

/**
 * 统一请求
 */
import java.io.Serializable;
import java.util.Arrays;


public class Request implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7033707301911915196L;


    private String className;
    private String methodName;
    private Class<?>[] types;
    private Object[]  params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public  Class<?>[] getTypes() {
        return types;
    }

    public void setTypes( Class<?>[] types) {
        this.types = types;
    }

    public Object[]  getParams() {
        return params;
    }

    public void setParams(Object[]  params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Request{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", types=" + Arrays.toString(types) +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}

