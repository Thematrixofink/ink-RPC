package com.ink.consumer.CGlib;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ink.rpc.model.RPCRequest;
import com.ink.rpc.model.RPCResponse;
import com.ink.rpc.serializer.Serializer;
import com.ink.rpc.serializer.SerializerImpl;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 *
 */
public class CGlibServiceProxy implements MethodInterceptor {

    /**
     *
     * @param o 代理对象
     * @param method 调用的真实对象中的方法实例
     * @param objects 实际参数，可以是0-N个
     * @param methodProxy 代理对象中的方法的Method实例
     * @return 返回结果
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //指定序列化器
        Serializer serializer = new SerializerImpl();

        //发送请求
        RPCRequest request = RPCRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(objects)
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
