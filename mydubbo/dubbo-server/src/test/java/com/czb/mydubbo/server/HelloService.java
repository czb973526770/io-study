package com.czb.mydubbo.server;

import com.czb.mydubbo.api.IHelloService;
import com.czb.mydubbo.server.annotation.RpcAnnotation;

/**
 * 测试的服务
 */

@RpcAnnotation (IHelloService.class)
public class HelloService implements IHelloService {


    public String sayHello(String str) {
        return "hello resp:" + str;
    }
}
