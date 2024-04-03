package com.yupi.yurpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信心
 */
@Data
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务的版本号
     */
    private String serviceVersion = "1.0";


    /**
     * 服务地址
     */
    private String serviceAddress;

    /**
     * 服务房租
     */
    private String serviceGroup;

    /**
     * 服务主机
     */
    private String serviceHost;

    /**
     * 服务端口
     */
    private Integer servicePort;

    /**
     * 获取服务键名
     * @return 服务组名
     */
    public String getServiceKey(){
        return String.format("%s:%s",serviceName,serviceVersion);
    }

    public String getServiceNodeKey() {
        return String.format("%s/%s",getServiceKey(),servicePort);
    }

//    public String getServiceAddress(){
//        if(!StrUtil.contains(serviceHost,"http")){
//            return String.format("http://%s:%s",serviceHost,servicePort);
//        }
//        return String.format("%s:%s",serviceHost,servicePort);
//    }
}
