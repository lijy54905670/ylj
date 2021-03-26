package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    ISysMenuService iSysMenuService;

    @RequestMapping("/test")
    public List<SysMenu> test(){
        List<SysMenu> sysMenus = iSysMenuService.selectMenuAll(1l);
        return sysMenus;
    }
}
