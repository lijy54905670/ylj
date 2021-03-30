package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.web.request.TargetVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/system/mark")
public class MarkController {

    String prefix = "mark";

    @GetMapping()
    public String markHtml(HttpSession session){

        return prefix + "/mark";
    }
}
