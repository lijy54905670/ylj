package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysRole;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.mapper.RoleRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService<RoleRepository, SysRole,Long> {

    @Autowired
    SysUserServiceImpl sysUserService;

    public List<SysRole> roleList(){
        List<SysRole> sysRoles = bizRepository.roleList();
        return sysRoles;
    }

    /**
     * 根据id查询权限详细
     * @param id
     * @return
     */
    public SysRole role(Long id){
        SysRole role = bizRepository.role(id);
        return role;
    }

    /**
     * 查询已分配权限人员
     * @return
     */
    public List<SysUser> allocatedList(Long rId){
        List<SysUser> sysUsers = sysUserService.allocatedList(rId);
        return sysUsers;
    }

    /**
     * 新增没有徐指南针
     * @return
     */
    public List<SysUser> unallocatedList(Long rId){
        List<SysUser> sysUsers = sysUserService.unAllocateList(rId);
        return sysUsers;
    }


    /**
     * 添加用户权限
     * @param rId
     * @param uIds
     * @return
     */
    @Transactional
    public int addSelectAll(Long rId,String uIds){
        List<Long> userIds = Arrays.stream(uIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
        int j = 0;
        for (int i = 0; i < userIds.size(); i++) {
            j = bizRepository.addSelectAll(rId,userIds.get(i));
        }
        return j;
    }

    /**
     * 删除用户权限
     */
    @Transactional
    public int removeSelectAll(Long rId,String uIds){
        Set<Long> userIds = Arrays.stream(uIds.split(",")).map(Long::parseLong).collect(Collectors.toSet());
        int j = 0;
        j = bizRepository.deleteSelect(rId, userIds);
        return j;
    }

    /**
     * 修改角色
     */
    @Transactional
    public int editRole(RoleRequest roleRequest){
        SysRole sysRole = bizRepository.getOne(roleRequest.getRoleId());
        EntityUtils.copyPropertiesIgnoreNull(roleRequest,sysRole);
        save(sysRole);
        Long roleId = roleRequest.getRoleId();
        int i = bizRepository.deleteAllByRoleId(roleId);
        int i2 = 0;
        String[] split = roleRequest.getMenuIds().split(",");
        if (i >= 0){
            for (int i1 = 0; i1 < split.length; i1++) {
                i2 = bizRepository.insertRole(roleId,split[i1]);
            }

        }
        return i2;
    }

}
