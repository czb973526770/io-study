package com.czb.mydubbo.server;

import com.czb.mydubbo.api.IGoodbyeService;
import com.czb.mydubbo.api.IHelloService;
import com.czb.mydubbo.core.NettyServer;
import com.czb.mydubbo.core.ServiceContainer;
import com.czb.mydubbo.core.ZkConfig;

/**
 */
public class Main {
    public static void main (String[] args) throws Exception {


        IHelloService helloService = new HelloService();
        IGoodbyeService goodbyeService = new GoodbyeService();

        //模拟spring容器
        ServiceContainer.set(IHelloService.class.getName(), helloService);
        ServiceContainer.set(IGoodbyeService.class.getName(), goodbyeService);

        //发布服务
        IServerRegister serverRegister = new ServerRegisterImpl();
        serverRegister.publishService(helloService, goodbyeService);

        NettyServer nettyServer = new NettyServer();
        //启动服务监听
        nettyServer.bind(ZkConfig.servicePort);
    }
}
