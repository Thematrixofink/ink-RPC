package com.ink.provider;

import com.ink.common.service.UserService;
import com.ink.provider.service.UserServiceImpl;
import com.ink.rpc.registry.LocalRegistry;
import com.ink.rpc.server.HttpServer;
import com.ink.rpc.server.VertxHttpServer;

/**
 * 简单的服务提供者实例
 *
 */
public class ServiceProvider
{
    public static void main( String[] args )
    {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 提供服务
        //启动Vertx服务器并监听8081端口
        HttpServer httpServer = new VertxHttpServer();
        httpServer.start(8081);
    }
}
