package com.yupi.yurpc.serializer;

import com.yupi.yurpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(String key) {
        Serializer serializer = SpiLoader.getInstance(Serializer.class,key);
        if (serializer == null) {
            return DEFAULT_SERIALIZER;
        }
        return serializer;
    }
//    /**
//     * 序列化映射
//     *
//     */
//    private static final Map<String ,Serializer> KEY_SERIALIZE_MAP = new HashMap<String, Serializer>(){
//        {
//            put(SerializerKeys.JSON, new JsonSerializer());
//            put(SerializerKeys.KYRO, new KryoSerializer());
//            put(SerializerKeys.HESSIAN, new HessianSerializer());
//            put(SerializerKeys.JDK, new JdkSerializer());
//        }
//    };
//    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZE_MAP.get("jdk");
//    /**
//     * 获取实例
//     */
//    public static Serializer getInstance (String key){
//        Serializer serializer = KEY_SERIALIZE_MAP.get(key);
//        if(serializer == null){
//            return DEFAULT_SERIALIZER;
//        }
//        return serializer;
//    }
}
