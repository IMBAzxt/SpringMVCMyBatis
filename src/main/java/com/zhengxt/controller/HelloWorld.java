package com.zhengxt.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.zhengxt.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ThinkPad
 */
@Controller
public class HelloWorld {

    private String words;

    /**
     * 简单页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "hello";
    }

    /**
     * 表单
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public ModelAndView student(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return new ModelAndView("student", "command", new Student());
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("SpringWeb") Student student,
            ModelMap model) {

        model.addAttribute("name", student.getName());
        model.addAttribute("age", student.getAge());
        model.addAttribute("id", student.getId());
        return "result";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "hello";
    }

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
