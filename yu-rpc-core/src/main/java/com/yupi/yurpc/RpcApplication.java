package com.yupi.yurpc;

import com.yupi.yurpc.config.RegistryConfig;
import com.yupi.yurpc.config.RpcConfig;
import com.yupi.yurpc.constant.RpcConstant;
import com.yupi.yurpc.registry.Registry;
import com.yupi.yurpc.registry.RegistryFactory;
import com.yupi.yurpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Set;

@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 获取配置
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpcConfig:{}", rpcConfig);
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);

    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig = new RpcConfig();
        log.info("rpc init ,config = {}",newRpcConfig.toString());
        //注册中心初始化
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
            log.info("rpc 初始化 ,config = {}",newRpcConfig.toString());
        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }
        init(newRpcConfig);


    }

    /**
     * 获取配置
     * 双检查锁单例模式的实现，在获取配置的时候采用init方法初始化配置
     * 为了便于扩展，支持传入配置对象
     * 如果不传入，则默认调用前面写好的ConfigUtils来进行加载
     */
    public static RpcConfig getRpcConfig() {
        //todo 加载配置文件 的时候加上双检查锁
        if(rpcConfig == null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        LinkedHashMap<Integer,Integer> map  = new LinkedHashMap<>(2,0.75f,true);
        return rpcConfig;
    }
    
    
}
