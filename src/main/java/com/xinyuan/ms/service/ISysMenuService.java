package com.xinyuan.ms.service;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService
{

    /**
     * 查询菜单集合
     *
     * @param userId 用户ID
     * @return 所有菜单信息
     */
    public List<SysMenu> selectMenuAll(Long userId);

//    /**
//     * 根据用户ID查询权限
//     *
//     * @param userId 用户ID
//     * @return 权限列表
//     */
//    public Set<String> selectPermsByUserId(Long userId);

}
