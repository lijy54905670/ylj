package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.mapper.SysMenuRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuRepository,SysMenu,Long> implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";


    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll(Long userId)
    {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId))
        {
            menuList = bizRepository.SelectMenuAll();
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
//    @Override
//    public Set<String> selectPermsByUserId(Long userId)
//    {
//        List<String> perms = menuMapper.selectPermsByUserId(userId);
//        Set<String> permsSet = new HashSet<>();
//        for (String perm : perms)
//        {
//            if (StringUtils.isNotEmpty(perm))
//            {
//                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
//            }
//        }
//        return permsSet;
//    }

}
