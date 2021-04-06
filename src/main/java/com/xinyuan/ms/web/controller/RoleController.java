package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysRole;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.service.impl.RoleService;
import com.xinyuan.ms.web.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分配权限
     */
    @GetMapping("/authUser/{id}")
    public String authUser(@PathVariable("id") Long id, Model model){
        SysRole role = roleService.role(id);
        model.addAttribute("role",role);
        return "role/authUser";
    }

    /**
     * 查询已经分配权限的用户
     * @param roleId
     * @return
     */
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(Long roleId){
        System.out.println(roleId);
        List<SysUser> sysUsers = roleService.allocatedList(roleId);
        return getDataTable(sysUsers);
    }

    /**
     * 给用户新增权限
     */
    @GetMapping("/authUser/selectUser/{id}")
    public String selectUser(@PathVariable("id") Long id,Model model){
        model.addAttribute("role",roleService.role(id));
        return "role/selectUser";
    }


    /**
     * 新增没有选择的用户
     * @param roleId
     * @return
     */
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(Long roleId){
        List<SysUser> sysUsers = roleService.unallocatedList(roleId);
        return getDataTable(sysUsers);
    }

    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public AjaxResult addSelectAll(Long roleId, String userIds){
        int i = roleService.addSelectAll(roleId, userIds);
        if (i != 0){
            return toAjax(1);
        }
        return AjaxResult.error("添加权限失败");
    }

    /**
     * 删除用户权限
     * @return
     */
    @RequestMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult deleteAuth(Long roleId, String userIds){
        int i = roleService.removeSelectAll(roleId, userIds);
        if (i != 0){
            return toAjax(1);
        }
        return AjaxResult.error("删除权限失败");
    }

    /**
     * 添加权限
     */
    @GetMapping("/add")
    public String addRole(){
        return "role/add";
    }

    /**
     * 修改权限
     */
    @GetMapping("/edit/{id}")
    public String editRole(@PathVariable("id") Long rId,Model model){
        model.addAttribute("role",roleService.role(rId));
        return "role/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(RoleRequest roleRequest){
        int i = roleService.editRole(roleRequest);
        if(i>0){
            return toAjax(1);
        }
        return AjaxResult.error("编辑失败");
    }
}
