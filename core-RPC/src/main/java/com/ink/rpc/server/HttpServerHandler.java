package com.ink.rpc.server;

import com.ink.rpc.model.RPCRequest;
import com.ink.rpc.model.RPCResponse;
import com.ink.rpc.registry.LocalRegistry;
import com.ink.rpc.serializer.Serializer;
import com.ink.rpc.serializer.SerializerImpl;
import com.sun.net.httpserver.HttpsServer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 请求处理器
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        //1.反序列化
        Serializer serializer = new SerializerImpl();
        System.out.println("接收到请求: "+request.method() + " " + request.uri());
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RPCRequest rpcRequest = null;
            try{
                //请求反序列化
                rpcRequest = serializer.deserialize(bytes,RPCRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            RPCResponse rpcResponse = new RPCResponse();
            if(rpcRequest == null){
                rpcResponse.setMessage("RPCRequest is null");
                response(request,rpcResponse,serializer);
                return;
            }

            try{
                //获取要调用的服务的实现类，通过反射来实现
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(),rpcRequest.getArgs());
                //封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            //响应
            response(request,rpcResponse,serializer);
        });


    }

    /**
     * 响应
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    public void response(HttpServerRequest request, RPCResponse rpcResponse, Serializer serializer){
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type","application/json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            httpServerResponse.end(Buffer.buffer());
            throw new RuntimeException(e);
        }

    }
}
