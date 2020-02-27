package com.czb.mydubbo.client;

import com.czb.mydubbo.client.balance.ILoadBalance;
import com.czb.mydubbo.client.balance.RandomLoadBalance;
import com.czb.mydubbo.core.ZkConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * Created by yzy on 2018/11/6.
 */
public class ServiceDiscoveryImpl implements IServerDiscover {

    RetryPolicy retryPolicy;
    CuratorFramework client;
    List<String> repos;

    ServiceDiscoveryImpl () {
        retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString(ZkConfig.ZK_SERVER)
                .sessionTimeoutMs(2000)
                .connectionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace(ZkConfig.ZK_NAME_SPACE)
                .build();
        client.start();
    }

    public String discover (String serviceName) {
        String path = ZkConfig.ZK_REGISTER_PATH + serviceName;
        try {
            //  /registry/服务名----->下面有多个子节点，因为一个服务会部署多台机器,通过负载均衡算法返回ip:port
            repos = client.getChildren().forPath(path);

            if (repos == null || repos.size() == 0) {
                System.out.println("未发现该服务: " + serviceName);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ILoadBalance loadBalance = new RandomLoadBalance();
        return loadBalance.select(repos);
    }
}
