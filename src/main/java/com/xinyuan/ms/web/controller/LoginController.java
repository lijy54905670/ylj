package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.DeptServiceImpl;
import com.xinyuan.ms.service.impl.SysUserServiceImpl;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.ui.Model;
import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.xinyuan.ms.common.entity.AjaxResult.error;

@Controller
public class LoginController extends BaseController{

    @Autowired
    ISysMenuService iSysMenuService;

    @Autowired
    SysUserServiceImpl sysUserService;

    @Autowired
    DeptServiceImpl deptService;


    @RequestMapping("/login1")
    public String loign1(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model){
        List<SysMenu> menus = iSysMenuService.selectMenuAll(1l);
        model.addAttribute("menus",menus);
        return "index1";
    }

    @RequestMapping("/system/user/profile")
    public String profile(){
        int i = 0;
        return "hello";
    }

    @RequestMapping("/system/user")
    public String userList(){
        return "user";
    }

    @RequestMapping("/system/user/list")
    @ResponseBody
    public TableDataInfo userListInfo(SysUser user){
        List<SysUserVo> list = sysUserService.getList(user);
        return getDataTable(list);
    }

    @GetMapping("system/dept/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> test = deptService.test();
        return test;
    }


    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
                                 @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptByDeptId(deptId));
        mmap.put("excludeId", excludeId);
        return "tree";
    }

    @GetMapping("/system/user/add")
    public String add(ModelMap mmap)
    {
//        mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
//        mmap.put("posts", postService.selectPostAll());
        return "add";
    }

    /**
     * 添加用户
     * */
    @PostMapping("/system/user/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        if(1 != 1){
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        user.setDelFlag("0");
        sysUserService.save(user);
        return toAjax(1);
    }
}
