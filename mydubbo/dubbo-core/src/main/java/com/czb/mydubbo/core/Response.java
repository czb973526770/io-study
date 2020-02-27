package com.czb.mydubbo.core;

/**
 * 统一响应对象
 */
import java.io.Serializable;

public class Response implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6236340795725143988L;
    public String body;

    public Response(String body) {
        this.body = body;
    }
}

