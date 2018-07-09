package com.xinyuan.ms.service;

import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.entity.Role;
import com.xinyuan.ms.mapper.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author hwz
 */
@Service
public class RoleService extends BaseService<RoleRepository, Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 新增异常处理
     *
     * @param role
     */
    @Transactional(rollbackFor = Exception.class)
    public Message saveRole(Role role) {
        save(role);
        return ResultUtil.success("新增成功");
    }


    public void removeRole(List<Long> ids) {
        for (Long i : ids) {
            remove(i);
        }
    }

    public Role findByRoleNameAndDeleted(String roleName,int deleted) {
        return roleRepository.findByRoleNameAndDeleted(roleName, 0);
    }

    public Role findById(Long id) {
        return roleRepository.findByIdAndDeleted(id, 0);
    }
}
