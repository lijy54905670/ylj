package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysPeriod;
import com.xinyuan.ms.entity.SysTarget;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.mapper.PeriodRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodService extends BaseService<PeriodRepository,SysPeriod,Long> {

    public List<SysPeriod> period(){
        List<SysPeriod> period = bizRepository.period();
        return period;
    }


    public List<Ztree> periodTree(){
        List<SysPeriod> sysPeriods = bizRepository.periodList(null);
        return initZtree(sysPeriods);
    }

    public SysPeriod selectDeptByDeptId(Long periodId){
        List<SysPeriod> sysPeriods = bizRepository.periodList(periodId);
        if (sysPeriods != null){
            return sysPeriods.get(0);
        }
        return null;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysPeriod> deptList)
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
    public List<Ztree> initZtree(List<SysPeriod> deptList, List<String> roleDeptList)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = false;
        for (SysPeriod dept : deptList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(dept.getPeriodId());
            ztree.setpId(dept.getParentId());
            ztree.setName(dept.getTitle());
            ztree.setTitle(dept.getTitle());
            if (isCheck)
            {
                ztree.setChecked(roleDeptList.contains(dept.getPeriodId() + dept.getTitle()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
