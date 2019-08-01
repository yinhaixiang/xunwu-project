package com.sean.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloCtrl {


    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String esCondition() {
        String str  = "22233333333333";
        System.out.println(str);
        return str;
    }



}
