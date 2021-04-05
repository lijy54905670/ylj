package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.common.util.MD5Util;
import com.xinyuan.ms.common.util.ReflectionUtils;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.exception.BaseException;
import com.xinyuan.ms.mapper.SysUserRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends BaseService<SysUserRepository, SysUser,Long> {

    @Autowired
    DeptServiceImpl deptService;

    /*通过deptId查询用户*/
    public List<SysUserVo> getList(SysUser user){
        List<SysUser> sysUsers = bizRepository.selectUserList(user.getDeptId());
        List<SysUserVo> sysUserVos = new ArrayList<>();
        for (int i = 0; i < sysUsers.size(); i++) {
            Long deptId= sysUsers.get(i).getDeptId();
            SysDept sysDept = deptService.selectDeptByDeptId(deptId);
            SysUserVo sysUserVo = new SysUserVo();
            EntityUtils.copyPropertiesIgnoreNull(sysUsers.get(i),sysUserVo);
            sysUserVo.setDept(sysDept);
            sysUserVos.add(sysUserVo);
        }
        return sysUserVos;
    }

    /**
     * 通过用户id查找用户
     * @param id
     * @return
     */
    public SysUser selectUserByid(Long id){
        SysUser sysUser = bizRepository.selectUserByid(id);
        return sysUser;
    }

    /**
     * 根据部门id查找部门
     * @param deptId
     * @return
     */
    public SysDept selectUserDept(Long deptId){
        return deptService.selectDeptByDeptId(deptId);
    }

    /**
     * 删除用户
     * @param ids
     * @return
     * @throws BaseException
     */
    public int remove(String ids) throws BaseException {
        String[] split = ids.replace("(", "").replace(")", "").split(",");
        for (int i = 0; i < split.length; i++) {
            SysUser entity = bizRepository.findOne(Long.parseLong(split[i]));
            if (entity != null) {
                if (ReflectionUtils.hasField(entity, "delFlag")) {
                    ReflectionUtils.invokeSetter(entity, "delFlag", "1");
                }
                bizRepository.save(entity);
            }
        }
        return split.length;

    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param session
     * @return
     */
    public boolean login(String userName, String password, HttpSession session, Model model){
        SysUser sysUser = bizRepository.selectUserByName(userName);
        if (sysUser == null){
            return false;
        }else {
            String encryptPassword = encryptPassword(password);
            if (!sysUser.getPassword().equals(encryptPassword)){
                return false;
            }
        }
        model.addAttribute("user",sysUser);
        session.setAttribute("user",sysUser);
        return true;
    }

    /**
     * 密码加密
     * @param pwd
     * @return
     */
    public String encryptPassword(String pwd){
        String encryptPwd = MD5Util.MD5(pwd);
        return encryptPwd;
    }

    /**
     * 通过用户id集合查询用户
     */
    public List<SysUser> getUserByIds(String ids,Set<Long> exceptIds){
        List<SysUser> userByIds = new ArrayList<>();
        if (ids != null && !ids.trim().equals("")){
            Set<Long> collect = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toSet());
            boolean b = collect.removeAll(exceptIds);
            userByIds = bizRepository.getUserByIds(collect);
        }
        return userByIds;
    }

    /**
     * 更新用户
     */
    public void updateUser(SysUser sysUser,HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        EntityUtils.copyPropertiesIgnoreNull(sysUser,user);
        save(user);
    }

}
