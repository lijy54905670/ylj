package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.DeptServiceImpl;
import com.xinyuan.ms.service.impl.SysMenuServiceImpl;
import com.xinyuan.ms.service.impl.SysUserServiceImpl;
import com.xinyuan.ms.web.vo.SysMenuVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.ui.Model;
import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.List;
import static com.xinyuan.ms.common.entity.AjaxResult.error;

@Controller
public class LoginController extends BaseController{

    @Autowired
    SysMenuServiceImpl iSysMenuService;

    @Autowired
    SysUserServiceImpl sysUserService;

    @Autowired
    DeptServiceImpl deptService;

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/login1")
    public String loign1(){
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password, Model model, HttpSession session){
        if ( sysUserService.login(username, password,session,model)){
            List<SysMenuVo> menus = iSysMenuService.selectMenuAll(1l);
            model.addAttribute("menus",menus);
            return success();
        }else {
            String msg = "用户名或密码不存在";
            return error(msg);
        }
    }

    /**
     * 跳转index页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        List<SysMenuVo> menus = iSysMenuService.selectMenuAll(1l);
        model.addAttribute("menus",menus);
        return "index1";
    }

    @RequestMapping("/system/user/profile")
    public String profile(Model model,HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        int i = 0;
        return "hello";
    }

    /**
     * 跳转user.html页面
     * @return
     */
    @RequestMapping("/system/user")
    public String userList(HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        int i = 1;
        return "user/user";
    }

    /**
     * 根据部门id查询用户
     * @param user
     * @return
     */
    @RequestMapping("/system/user/list")
    @ResponseBody
    public TableDataInfo userListInfo(SysUser user){
        List<SysUserVo> list = sysUserService.getList(user);
        return getDataTable(list);
    }

    /**
     * 获取左侧部门树
     * @return
     */
    @GetMapping("system/dept/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> test = deptService.test();
        return test;
    }


    /**
     * 查询部门树
     * @param deptId
     * @param excludeId
     * @param mmap
     * @return
     */
    @GetMapping(value = { "/system/dept/selectDeptTree/{deptId}", "/system/dept/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
                                 @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptByDeptId(deptId));
        mmap.put("excludeId", excludeId);
        return "dept/tree";
    }



}
