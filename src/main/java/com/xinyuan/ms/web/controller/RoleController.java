package com.xinyuan.ms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/role")
public class RoleController {

    /**
     * 跳转权限页面
     * @return
     */
    @GetMapping()
    public String role(){
        return "role/role";
    }

    @PostMapping("/list")
    public void roleList(){

    }

}
