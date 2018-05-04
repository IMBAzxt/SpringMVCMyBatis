/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt.controller;

import com.zhengxt.dto.Response;
import com.zhengxt.entity.Users;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用@RestController，可以省略掉@ResponseBody注解，即返回结果序列化。
 *
 * @author ThinkPad
 */
@RestController
@RequestMapping(value = "api")
public class ApiREST {

    @GetMapping(value = "testGet/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testGet(@PathVariable("id") int id, @RequestParam("username") String username, @RequestHeader("Accept-Encoding") String encoding) {
        Users user = new Users();
        user.setId(id);
        user.setPassword("12345");
        user.setUsername(username);
        user.setPasswordSalt("12345");
        return new Response<Users>().success(user);
    }

    @PutMapping(value = "testPut", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testPut(@RequestBody Users users) {
        return new Response<Users>().success(users);
    }

    @PostMapping(value = "testPost", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testPost(@RequestBody Users users, HttpServletRequest request) {
        return new Response<Users>().success(users);
    }

    @DeleteMapping(value = "testDelete/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Response testDelete(@PathVariable int id) {
        return new Response<Integer>().success(id);
    }
}
