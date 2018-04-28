/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.dto;

/**
 *
 * @author ThinkPad
 * @param <T>
 */
public class Response<T> {
    private static final String OK = "OK";
    private static final String ERROR = "ERROR";
    private Meta meta;
    private T data;

    public Response success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public Response success(T data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public Response failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public Response failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
