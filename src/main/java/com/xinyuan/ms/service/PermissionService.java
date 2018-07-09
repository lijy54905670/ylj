package com.xinyuan.ms.service;

import com.xinyuan.ms.entity.Permission;
import com.xinyuan.ms.entity.PermissionRole;
import com.xinyuan.ms.mapper.PermissionRepository;
import com.xinyuan.ms.web.vo.PermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liang
 */
@Service
public class PermissionService extends BaseService<PermissionRepository, Permission, Long> {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionRoleService permissionRoleService;

    public List<Permission> findByDeleted() {

        return permissionRepository.findByDeleted(0);
    }

    public Permission findById(Long id) {
        return permissionRepository.findByIdAndDeleted(id, 0);
    }

    public List<Permission> findAll() {
        return permissionRepository.findByDeleted(0);
    }

    public List<Permission> findByPid(Long pid) {
        return permissionRepository.findByPidAndDeleted(pid, 0);
    }

    /**
     * 查用户对应权限
     *
     * @param rid
     * @return
     */
    public List<PermissionVo> findByRoleId(long rid) {
        List<Permission> permissions = new ArrayList<>();

//        Role role = userRoleService.findRole(uid);

        List<PermissionRole> permissionRoles = permissionRoleService.findByRid(rid);


        List<PermissionVo> list = new ArrayList<>();

        if (permissionRoles.size() > 0) {

            for (PermissionRole permissionRole : permissionRoles) {

                permissions.add(findById(permissionRole.getPid()));

            }
        }


        //查询所有权限
        List<Permission> permissionList = findAll();


        List<PermissionVo> permissionVoList = new ArrayList<>();

        if (permissionList.size() > 0) {
            for (Permission permission : permissionList) {

                PermissionVo permissionVo = new PermissionVo();

                BeanUtils.copyProperties(permission, permissionVo);

                for (Permission p : permissions) {

                    if (p.getId().equals(permission.getId())) {

                        permissionVo.setStatus(1);
                        break;

                    } else {

                        permissionVo.setStatus(0);

                    }

                }

                if (permissionVo.getPid() == 0) {
                    list.add(permissionVo);
                }
                permissionVoList.add(permissionVo);

            }

            for (PermissionVo permissionVo : list) {
                permissionVo.setChild(getChild(permissionVo.getId(), permissionVoList));
            }
        }


        return list;
    }

    /**
     * 查询子模块
     *
     * @param id
     * @param permissionVoList
     * @return
     */
    public List<PermissionVo> getChild(Long id, List<PermissionVo> permissionVoList) {
        // 子菜单
        List<PermissionVo> childList = new ArrayList<>();
        for (PermissionVo permissionVo : permissionVoList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (permissionVo.getPid() != 0) {
                if (permissionVo.getPid().equals(id)) {
                    childList.add(permissionVo);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (PermissionVo permissionVo : childList) {
            // 判断是否还有还有子菜单

            if (findByPid(permissionVo.getId()).size() > 0) {
                // 递归
                permissionVo.setChild(getChild(permissionVo.getId(), permissionVoList));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
