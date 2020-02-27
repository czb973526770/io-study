package com.czb.mydubbo.server;

import com.czb.mydubbo.core.ZkConfig;
import com.czb.mydubbo.server.annotation.RpcAnnotation;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 服务注册
 */
public class ServerRegisterImpl implements IServerRegister {

    RetryPolicy retryPolicy;
    CuratorFramework client;

    ServerRegisterImpl (){
        retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString(ZkConfig.ZK_SERVER)
                .sessionTimeoutMs(2000)
                .connectionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace(ZkConfig.ZK_NAME_SPACE)
                .build();
        client.start();
    }

    public void register(String serviceName, String address) {

        String servicePath = ZkConfig.ZK_REGISTER_PATH + serviceName;
        try {
            if(client.checkExists().forPath(servicePath) == null){

                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath,"0".getBytes());

            }

            String addressPath = servicePath + "/" + address;
            String rsNode = client.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath,"0".getBytes());
            System.out.println("服务"+serviceName+"注册成功"+rsNode);
            //ZK的地址为/severregister/服务名/实例地址
            //服务名持久化节点，地址临时节点。服务关闭，临时节点删除，服务名需要保留(可能有其他实例)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void publishService(Object... services){
        for(Object service : services){
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            register(serviceName, ZkConfig.serviceAddress);
        }
    }
}
