package com.ink.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装调用方法得到的返回值以及调用的信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RPCResponse implements Serializable {
    /**
     * 响应数据
     */
    private Object Data;

    /**
     * 响应数据类型
     */
    private Class<?> dataType;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 异常信息
     */
    private Exception exception;
}
