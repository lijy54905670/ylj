package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.service.impl.SysUserServiceImpl;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.xinyuan.ms.common.entity.AjaxResult.error;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController{

    @Autowired
    SysUserServiceImpl iSysUserService;

    /**
     * 跳转添加用户页面
     * @param mmap
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        return "add";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        if(1 != 1){
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        user.setDelFlag("0");
        iSysUserService.save(user);
        return toAjax(1);
    }

    /**
     * 跳转修改用户页面
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        SysUser sysUser = iSysUserService.selectUserByid(userId);
        SysDept sysDept = iSysUserService.selectUserDept(sysUser.getDeptId());
        SysUserVo sysUserVo = new SysUserVo();
        EntityUtils.copyPropertiesIgnoreNull(sysUser,sysUserVo);
        sysUserVo.setDept(sysDept);
        mmap.put("user", sysUserVo);
        return "user/edit1";
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysUser user)
    {
        iSysUserService.updateId(user);
        return toAjax(1);
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int remove = iSysUserService.remove(ids);
        return toAjax(remove);
    }

    /**
     * 跳转重置密码页面
     */
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user",  iSysUserService.selectUserByid(userId));
        return "user/resetPwd";
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user){
        user.setPassword(encryptPassword(user.getPassword()));
        iSysUserService.updateId(user);
        return success();
//        return error();
    }
}
