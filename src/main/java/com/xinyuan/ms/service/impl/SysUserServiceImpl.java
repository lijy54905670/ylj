package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.common.util.ReflectionUtils;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.exception.BaseException;
import com.xinyuan.ms.mapper.SysUserRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public SysUser selectUserByid(Long id){
        SysUser sysUser = bizRepository.selectUserByid(id);
        return sysUser;
    }

    public SysDept selectUserDept(Long deptId){
        return deptService.selectDeptByDeptId(deptId);
    }

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

}
