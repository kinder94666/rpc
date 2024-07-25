package com.yupi.yurpc.config;

import com.yupi.yurpc.constant.RpcConstant;
import com.yupi.yurpc.serializer.SerializerKeys;
import com.yupi.yurpc.spi.SpiLoader;
import lombok.Data;

@Data
public class RpcConfig {
    private String host = "kinder-rpc";

    private String version = "1.0.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private boolean mock = false;

    private String serializer = SerializerKeys.JDK;

    private RegistryConfig registryConfig = new RegistryConfig();

    public boolean isMock() {
        return mock;
    }

}
