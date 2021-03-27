package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysTarget;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.mapper.TargetRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TargetService extends BaseService<TargetRepository, SysTarget,Long> {

    public List<SysTarget> targetList(){
        List<SysTarget> sysTargets = bizRepository.targetList();
        return sysTargets;
    }

    public List<Ztree> targeTree(){
        List<SysTarget> sysTargets = bizRepository.targetList();
        List<Ztree> ztrees = initZtree(sysTargets);
        return ztrees;
    }


    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysTarget> deptList)
    {
        return initZtree(deptList, null);
    }


    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysTarget> deptList, List<String> roleDeptList)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = false;
        for (SysTarget dept : deptList)
        {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getTargetId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getContent());
                ztree.setTitle(dept.getContent());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getTargetId() + dept.getContent()));
                }
                ztrees.add(ztree);
        }
        return ztrees;
    }
}
