package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.service.impl.SysMenuServiceImpl;
import com.xinyuan.ms.web.vo.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    SysMenuServiceImpl iSysMenuService;

    @RequestMapping("/test")
    public List<SysMenuVo> test(){
        List<SysMenuVo> sysMenus = iSysMenuService.selectMenuAll(1l);
        return sysMenus;
    }
}
