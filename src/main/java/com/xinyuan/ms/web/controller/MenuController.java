package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.SysMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    SysMenuServiceImpl sysMenuService;

    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData(Long roleId){
        List<Ztree> ztrees = sysMenuService.roleMenuTreeData(roleId);
        return ztrees;
    }

}
