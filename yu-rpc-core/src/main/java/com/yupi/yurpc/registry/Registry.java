package com.yupi.yurpc.registry;

import com.yupi.yurpc.config.RegistryConfig;
import com.yupi.yurpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Registry {
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务
     *
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;



    void unRegister(ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException;

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 服务发现
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

}
