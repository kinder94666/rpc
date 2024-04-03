package com.yupi.yurpc.registry;

import com.yupi.yurpc.config.RegistryConfig;
import com.yupi.yurpc.model.ServiceMetaInfo;
import io.etcd.jetcd.kv.GetResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
     * 注册中心测试
     *
     */
    public class RegistryTest {
        final static Registry registry = new EtcdRegistry();
        final static RegistryConfig registryConfig= new RegistryConfig();
        static {
            registryConfig.setAddress("http://localhost:2379");
            registry.init(registryConfig);
        }
        @Before
        public void init() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("http://localhost:2379");
            registry.init(registryConfig);
        }
        @Test
        public void register() throws Exception {
//            RegistryConfig registryConfig = new RegistryConfig();
//            registryConfig.setAddress("http://localhost:2379");
//            registry.init(registryConfig);
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1235);
            registry.register(serviceMetaInfo);
            serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("2.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.register(serviceMetaInfo);
        }
        @Test
        public void unRegister() throws ExecutionException, InterruptedException {
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            serviceMetaInfo.setServiceHost("localhost");
            serviceMetaInfo.setServicePort(1234);
            registry.unRegister(serviceMetaInfo);
        }
        @Test
        public void serviceDiscovery() {
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName("myService");
            serviceMetaInfo.setServiceVersion("1.0");
            String serviceKey = serviceMetaInfo.getServiceKey();
            List<ServiceMetaInfo> serviceMetaInfoList =
                    registry.serviceDiscovery(serviceKey);
            System.out.println(serviceMetaInfoList);
            Assert.assertNotNull(serviceMetaInfoList);
        }

}
