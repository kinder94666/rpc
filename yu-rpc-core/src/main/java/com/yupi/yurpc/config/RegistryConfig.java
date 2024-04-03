package com.yupi.yurpc.config;

import lombok.Data;

@Data
public class RegistryConfig {
    private String registry = "etcd";

    private String address = "http://localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * ttl
     */
    private Long timeout = 10000L;


}
