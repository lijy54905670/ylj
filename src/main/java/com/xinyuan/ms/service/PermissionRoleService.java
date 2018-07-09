package com.xinyuan.ms.service;

import com.xinyuan.ms.entity.PermissionRole;
import com.xinyuan.ms.mapper.PermissionRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: hwz
 * @Date: Created in 16:22 2018/4/10.
 */
@Service
public class PermissionRoleService extends BaseService<PermissionRoleRepository, PermissionRole, Long> {

    @Autowired
    private PermissionRoleRepository permissionRoleRepository;

    public List<PermissionRole> findByRid(Long rid) {
        return permissionRoleRepository.findByRidAndDeleted(rid, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<PermissionRole> savePermissionRole(List<PermissionRole> permissionRoles) {
        if (permissionRoles.size() > 0) {
            List<PermissionRole> permissionRoleList = findByRid(permissionRoles.get(0).getRid());

            //删除旧的权限
            if (permissionRoleList.size() > 0) {
                for (PermissionRole permissionRole : permissionRoleList) {
                    remove(permissionRole.getId());
                }
            }

//            if (permissionRoles.get(0).getPid() != 0) {
            //添加新的权限
                for (PermissionRole permissionRole : permissionRoles) {

                    this.save(permissionRole);
                }
//            }

        }

        return permissionRoles;
    }

}
