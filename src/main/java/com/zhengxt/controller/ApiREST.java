/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.controller;

import com.zhengxt.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用@RestController，可以省略掉@ResponseBody注解，即返回结果序列化。
 *
 * @author ThinkPad
 */
@RestController
@RequestMapping(value = "api")
public class ApiREST {

    @GetMapping(value = "testGet", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testGet() {
        return new Response().success("test rest api,method:get");
    }

    @PutMapping(value = "testPut", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testPut() {
        return new Response().success("test rest api,method:get");
    }

    @PostMapping(value = "testPost", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testPost() {
        return new Response().success("test rest api,method:post");
    }

    @DeleteMapping(value = "testDelete", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testDelete() {
        return new Response().success("test rest api,method:delete");
    }
}
