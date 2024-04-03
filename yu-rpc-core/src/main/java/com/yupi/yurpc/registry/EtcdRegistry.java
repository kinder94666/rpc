package com.yupi.yurpc.registry;


import cn.hutool.json.JSONUtil;
import com.yupi.yurpc.config.RegistryConfig;
import com.yupi.yurpc.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class EtcdRegistry implements Registry{
    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        // create client using endpoints
        Client client = Client.builder().endpoints("http://localhost:2379")
                .build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("woqiao".getBytes());
        ByteSequence value = ByteSequence.from("yuansheng".getBytes());
        // put the key-value
        kvClient.put(key, value).get();
        // get the CompletableFuture
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        // get the value from CompletableFuture
        GetResponse response = getFuture.get();
        System.out.println(response);
//        // delete the key
//        kvClient.delete(key).get();
    }


    private Client client;
    private KV kvClient;
    private static final String ETCD_ROOT_PATH = "rpc/";
    @Override
    public void init(RegistryConfig registryConfig) {
        client=Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        //创建lease
        Lease lease = client.getLeaseClient();

        //创建一个30秒的租约
        long leaseId = lease.grant(60).get().getID();

        //设置要存储的键值对
        String registry =  ETCD_ROOT_PATH+serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registry, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
        //将键和值联系起来
        PutOption po = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key,value,po).get();
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        // get the value from CompletableFuture
        GetResponse response = getFuture.get();
        System.out.println(response);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException {

//        ETCD_ROOT_PATH+serviceMetaInfo.getServiceNodeKey();

        System.out.println("===================="+ETCD_ROOT_PATH+serviceMetaInfo.getServiceNodeKey());
        CompletableFuture<DeleteResponse> delete = kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
        DeleteResponse deleteResponse = delete.get();
        System.out.println(deleteResponse);
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        if(kvClient!=null){
            kvClient.close();
        }
        if(client!=null){
            client.close();
        }
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        String searchPrefix = ETCD_ROOT_PATH+serviceKey+"/";
        GetOption getOption = GetOption.builder().isPrefix(true).build();
        List<KeyValue> kvs = null;
        try {
            kvs = kvClient.get(
                    ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                    getOption
            ).get().getKvs();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return kvs.stream().map(kv->{
            String value = kv.getValue().toString(StandardCharsets.UTF_8);
            return JSONUtil.toBean(value,ServiceMetaInfo.class);
        }).collect(Collectors.toList());
    }
}
