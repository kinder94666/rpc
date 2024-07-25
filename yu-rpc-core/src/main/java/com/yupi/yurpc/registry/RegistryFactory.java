package com.yupi.yurpc.registry;

import com.yupi.yurpc.spi.SpiLoader;

public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实力
     */
    public static Registry getInstance(String key){
        Registry instance =(Registry)SpiLoader.getInstance(Registry.class, key);
        if(instance == null){
            return DEFAULT_REGISTRY;
        }
        return instance;
    }
}
