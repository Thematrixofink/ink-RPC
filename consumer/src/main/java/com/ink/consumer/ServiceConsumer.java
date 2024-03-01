package com.ink.consumer;


import com.ink.common.model.User;
import com.ink.common.service.UserService;

/**
 * 简单的服务消费者实例
 *
 */
public class ServiceConsumer
{
    public static void main( String[] args )
    {
        //获取UserService的实例对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User("ink");
        //调用提供的服务
//        User returnUser = userService.getUser(user);
//        if(returnUser != null){
//            System.out.println(returnUser.getName());
//        }else{
//            System.out.println("returnUser为null!");
//        }

        User newUser = userService.getRevertUserName(user);
        System.out.println(newUser.getName());
    }
}
