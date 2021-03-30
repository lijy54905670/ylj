package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysTarget;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.mapper.TargetRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.request.TargetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TargetService extends BaseService<TargetRepository, SysTarget,Long> {

    @Autowired
    SysPeriodTargetService sysPeriodTargetService;


    public List<SysTarget> targetList(TargetVo targetVo){

        Long periodId = targetVo.getPeriodId();
        String targetIds = "";
        if (periodId != null && targetVo.getPeriodId()!=null) {
            targetIds = sysPeriodTargetService.selectTargetIds(periodId);
        }
        Set<Long> ids = new HashSet<>();
        String i = "";
        if (targetIds != null && !targetIds.equals("")) {
            ids = Arrays.stream(targetIds.split(",")).map(Long::parseLong).collect(Collectors.toSet());
            i = "1";
        }else {
            ids.add(-1L);
        }
        List<SysTarget> sysTargets ;
        if (targetVo != null && targetVo.getCla() !=null && targetVo.getCla().equals("period")){
            sysTargets = bizRepository.targetList1(i,ids);
        }else {
            sysTargets = bizRepository.targetList();
        }
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
