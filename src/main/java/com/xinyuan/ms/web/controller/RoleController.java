package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.entity.SysRole;
import com.xinyuan.ms.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController{

    @Autowired
    RoleService roleService;
    /**
     * 跳转权限页面
     * @return
     */
    @GetMapping()
    public String role(){
        return "role/role";
    }

    /**
     * 获取角色列表
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo roleList(){
        List<SysRole> sysRoles = roleService.roleList();
        return getDataTable(sysRoles);
    }

}
