package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.mapper.SysMenuRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.vo.SysMenuVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuRepository,SysMenu,Long> {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";


    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    public List<SysMenuVo> selectMenuAll(Long userId) {
        List<SysMenuVo> sysMenuVos = new ArrayList<>();

            List<SysMenu> menuList = bizRepository.SelectMenuAll(userId);
            if (menuList != null && menuList.size() > 0) {
                for (SysMenu sysMenu1 : menuList) {
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    EntityUtils.copyPropertiesIgnoreNull(sysMenu1, sysMenuVo);
                    List<SysMenu> sysMenus = new ArrayList<>();
                    for (SysMenu sysMenu2 : menuList) {
                        if (sysMenu1.getMenuId() == sysMenu2.getParentId()) {
                            sysMenus.add(sysMenu2);
                        }
                    }
                    sysMenuVo.setChildren(sysMenus);
                    if (sysMenus.size() > 0) {
                        sysMenuVos.add(sysMenuVo);
                    }
                }

        }
        return sysMenuVos;
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

    /**
     * 权限列表
     * @return
     */
    public List<Ztree> roleMenuTreeData(Long rId){
        List<Ztree> ztree = new ArrayList<Ztree>();
        List<SysMenu> menuList = bizRepository.SelectMenuAll();
        List<String> ids = bizRepository.ids(rId);
        List<String> collect = ids.stream().map(w -> w+"").collect(Collectors.toList());
        ztree = initZtree(menuList, collect, true);
        int i = 0;

        return ztree;

    }

    public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = (roleMenuList != null);
        for (SysMenu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId()+""));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(SysMenu menu, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }


}
