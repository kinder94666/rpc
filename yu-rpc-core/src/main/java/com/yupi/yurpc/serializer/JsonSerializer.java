package com.yupi.yurpc.serializer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupi.yurpc.config.RpcConfig;
import com.yupi.yurpc.model.RpcRequest;
import com.yupi.yurpc.model.RpcResponse;

import java.io.IOException;

public class JsonSerializer implements Serializer{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {

        T obj = OBJECT_MAPPER.readValue(bytes, type);
        if(obj instanceof RpcRequest){
            return handleRequest((RpcRequest)obj,type);
        }
        if(obj instanceof RpcConfig){
            return handleResponse((RpcResponse)obj,type);
        }
        return obj;

    }

    /**
     * 由于object的原始对象会被擦除，导致反序列化时会被作为linkedHashMap处理
     * 无法转成原始对象，因此这里做了特殊处理
     * @param rpcRequest 请求对象
     * @param type 类型
     * @return 返回处理对象
     * @param <T> 类型
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();
        //循环处理参数类型
        for(int i=0;i<parameterTypes.length;i++){
            Class<?> clazz = parameterTypes[i];
            //如果参数类型不同，则进行转换
            if(!clazz.isAssignableFrom(args[i].getClass())){
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes,clazz);
            }
        }

        return type.cast(rpcRequest);
        }


    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        Object data = rpcResponse.getData();
        if(data != null){
            byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(data);
            data = OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType());
            rpcResponse.setData(data);
        }
        return type.cast(rpcResponse);
    }
}
