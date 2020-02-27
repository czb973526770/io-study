package com.czb.mydubbo.server;

/**
 * 服务注册
 */
interface IServerRegister {
    void register (String serviceName, String address);
    void publishService (Object... services);
}
