package com.xinyuan.ms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login1")
    public String loign1(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index1";
    }

    @RequestMapping("/system/user/profile")
    public String profile(){
        int i = 0;
        return "hello";
    }


}
