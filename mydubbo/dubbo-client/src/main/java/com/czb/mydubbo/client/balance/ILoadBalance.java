package com.czb.mydubbo.client.balance;

import java.util.List;

/**
 * 负载均衡器
 */
public interface ILoadBalance {
    String select (List<String> repos);
}
