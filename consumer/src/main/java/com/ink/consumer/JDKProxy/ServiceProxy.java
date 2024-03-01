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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务动态代理(JDK动态代理)
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        Serializer serializer = new SerializerImpl();

        //发送请求
        RPCRequest request = RPCRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(request);
            byte[] result;
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8081")
                    .body(bodyBytes)
                    .execute();
            result = httpResponse.bodyBytes();
            RPCResponse response = serializer.deserialize(result, RPCResponse.class);
            return response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
