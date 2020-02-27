package com.czb.mydubbo.client;

import com.czb.mydubbo.api.IGoodbyeService;
import com.czb.mydubbo.api.IHelloService;

/**
 * 发送请求
 */
public class Main {
    public static void main (String[] args) {

        IServerDiscover serviceDiscovery = new ServiceDiscoveryImpl();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);


        IHelloService helloService = rpcClientProxy.create(IHelloService.class);
        IGoodbyeService goodbyeService = rpcClientProxy.create(IGoodbyeService.class);


        String str1 = helloService.sayHello("cloud");
        System.out.println("str=" + str1);

        String resp = goodbyeService.sayByeBye("struts");


        System.out.println("resp=" + resp);

    }
}
