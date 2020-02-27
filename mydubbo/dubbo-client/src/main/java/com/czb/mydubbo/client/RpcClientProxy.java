package com.czb.mydubbo.client;

import com.czb.mydubbo.core.NettyClient;
import com.czb.mydubbo.core.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理接口
 */
public class RpcClientProxy {
    IServerDiscover serverDiscover;
    public RpcClientProxy (IServerDiscover serverDiscover) {
        this.serverDiscover = serverDiscover;
    }

    public <T> T  create(final Class<T> interfaceClazz) {

         return (T) Proxy.newProxyInstance(interfaceClazz.getClassLoader(),
                 new Class<?>[]{interfaceClazz}, new InvocationHandler() {
                     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                         Request request = new Request();
                         request.setClassName(interfaceClazz.getName());
                         request.setMethodName(method.getName());
                         request.setTypes(method.getParameterTypes());
                         request.setParams(args);

                         String url = serverDiscover.discover(interfaceClazz.getName());

                         NettyClient nettyClient = new NettyClient();
                         String ip = url.split(":")[0];
                         String port = url.split(":")[1];

                         String respone = nettyClient.send(Integer.parseInt(port),ip,request);//请求dubbo获取结果

                         return respone;
                     }
                 });

    }
}
