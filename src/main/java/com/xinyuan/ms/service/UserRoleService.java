package com.xinyuan.ms.service;

import com.xinyuan.ms.entity.Role;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.entity.UserRole;
import com.xinyuan.ms.mapper.RoleRepository;
import com.xinyuan.ms.mapper.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liang
 */
@Service
public class UserRoleService extends BaseService<UserRoleRepository,UserRole,Long> {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public List<String> findRoles(Long uid) {

        List<String> list = userRoleRepository.findRoleName(uid);

        return list;
    }

    public Role findRole(Long uid) {

        UserRole userRole = userRoleRepository.findByUid(uid);

        Role role = roleRepository.findByIdAndDeleted(userRole.getRid(),0);

        return role;
    }



    public void remove(UserRole userRole){
        userRoleRepository.delete(userRole);
    }



    public UserRole findByUid(Long uid){
        return userRoleRepository.findByUidAndDeleted(uid,0);
    }

    public List<UserRole> findByRid(Long rid) {
        return userRoleRepository.findByRidAndDeleted(rid,0);
    }

    public List<UserRole> findByRidNot(Long rid) {
        return userRoleRepository.findByRidNotAndDeleted(rid,0);
    }

    /**
     * 查找客服
     * @return
     */
    public List<User> findByRoleNameAndRidAndUid(String rolename){
         Role role=roleService.findByRoleNameAndDeleted(rolename,0);
        List<User> list=new ArrayList<User>();
        if(role!=null){
            List<UserRole> userRoleList=userRoleRepository.findByRidAndDeleted(role.getId(),0);
            for(UserRole u:userRoleList){
                User user=userService.findById(u.getUid());
                list.add(user);
            }
        }
        return list;
    }

}
