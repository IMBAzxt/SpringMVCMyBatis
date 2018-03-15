/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhengxt;

import org.springframework.stereotype.Component;

/**
 *
 * @author ThinkPad
 */
@Component
public class HelloWorld {
    private String words;

    public void sayHello() {
        System.out.println(getWords());
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

}
