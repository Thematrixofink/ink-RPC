package com.ink.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简易的本地注册器
 * 和注册中心还是有区别的，注册中心的作用侧重于管理注册的服务、提供服务信息给消费者
 * 而本地服务注册器的作用是根据服务名获取到对应的实现类，是完成调用必不可少的模块
 */
public class LocalRegistry {
    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();


    /**
     * 注册服务
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName,Class<?> implClass){
        map.put(serviceName,implClass);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     * @return
     */
    public static boolean remove(String serviceName){
        map.remove(serviceName);
        return true;
    }

}
