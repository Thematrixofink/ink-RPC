package com.ink.consumer.JDKProxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ink.common.model.User;
import com.ink.common.service.UserService;
import com.ink.rpc.model.RPCRequest;
import com.ink.rpc.model.RPCResponse;
import com.ink.rpc.serializer.Serializer;
import com.ink.rpc.serializer.SerializerImpl;

import java.io.IOException;

/**
 * 用户服务静态代理
 * 十分麻烦，每一个方法都要写代码
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer = new SerializerImpl();

        //发送请求
        RPCRequest request = RPCRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{UserService.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(request);
            byte[] result;
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8081")
                    .body(bodyBytes)
                    .execute();
            result = httpResponse.bodyBytes();
            RPCResponse response = serializer.deserialize(result, RPCResponse.class);
            return (User)response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getRevertUserName(User user) {
        return null;
    }
}
