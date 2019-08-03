package com.sean.controller;

import com.sean.base.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeCtrl {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("name", "sean");
        int a = 1 / 0;
        return "index";
    }


    @GetMapping("/get")
    @ResponseBody
    public ApiResponse get(Model model) {
        int a = 1 / 0;
        return ApiResponse.ofMessage(200, "成功");
    }





}
