package com.ink.consumer.CGlib;

import net.sf.cglib.proxy.Enhancer;

/**
 * 代理工厂
 */
public class CGlibServiceProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass){
        //创建Enhancer对象，类似JDK动态代理的Proxy类
        Enhancer enhancer = new Enhancer();
        //设置代理对象的父类的字节码对象（Class类型的对象），指定代理对象的父类
        enhancer.setSuperclass(serviceClass);
        //设置回调函数，实现调用代理对象的方法时最终都会执行MethodInterceptor的子类实现
        //所以我们的CGlibServiceProxy需要实现MethodInterceptor接口，然后实现其方法
        enhancer.setCallback(new CGlibServiceProxy());
        //设置完参数之后，就可以反悔了
        return (T)enhancer.create();
    }
}
