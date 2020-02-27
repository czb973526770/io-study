package com.czb.mydubbo.server;

import com.czb.mydubbo.api.IGoodbyeService;
import com.czb.mydubbo.server.annotation.RpcAnnotation;

/**
 * 测试的服务
 */
@RpcAnnotation (IGoodbyeService.class)
public class GoodbyeService implements IGoodbyeService {
    public String sayByeBye(String str) {
        return "byebye resp:" + str;
    }
}
