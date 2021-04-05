package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.constant.UserConstants;
import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.mapper.DeptRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl extends BaseService<DeptRepository, SysDept,Long> {

    public List<Ztree> test(){
        List<SysDept> sysDepts = bizRepository.selectDeptList();
        List<Ztree> ztrees = initZtree(sysDepts);
        return ztrees;
    }

    public SysDept selectDeptByDeptId(Long deptId){
        SysDept sysDept = bizRepository.selectDeptByDeptId(deptId);
        return sysDept;
    }

    public List selectDeptLists(){
        return bizRepository.selectDeptLists();
    }


    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList)
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
    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = false;
        for (SysDept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }


    /**
     * 添加部门
     */
    public void addDept(SysDept sysDept){
        sysDept.setDelFlag("0");
        save(sysDept);
    }

    /**
     * 根据部门id查询部门详细信息
     */
    public SysDept getDeptById(Long id){
        SysDept sysDept = bizRepository.selectDeptByDeptId(id);
        return sysDept;
    }

    /**
     * 更新部门
     */
    public void updateDept(SysDept sysDept){
        SysDept one = bizRepository.findOne(sysDept.getDeptId());
        if (one != null){
            EntityUtils.copyPropertiesIgnoreNull(sysDept,one);
            save(one);
        }
    }

}
