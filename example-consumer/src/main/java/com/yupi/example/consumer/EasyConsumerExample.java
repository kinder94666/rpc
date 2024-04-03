package com.yupi.example.consumer;

import com.yupi.example.common.model.User;
import com.yupi.example.common.service.UserService;
import com.yupi.yurpc.proxy.ServiceProxy;
import com.yupi.yurpc.proxy.ServiceProxyFactory;
import com.yupi.yurpc.server.HttpServer;
import com.yupi.yurpc.server.VertxHttpServer;

import javax.net.ssl.HttpsURLConnection;

/**
 * 简易服务消费者示例
 *
 * @author kinder
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(8080);
//         静态代理
//        UserService userService = new UserServiceProxy();
//         动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yupi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
            System.out.println("============================================================================");
        } else {
            System.out.println("user == null");
        }
    }
}
