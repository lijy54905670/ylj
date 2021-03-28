package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysRole;
import com.xinyuan.ms.mapper.RoleRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends BaseService<RoleRepository, SysRole,Long> {

    public List<SysRole> roleList(){
        List<SysRole> sysRoles = bizRepository.roleList();
        return sysRoles;
    }
}
