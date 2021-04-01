package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.ReflectionUtils;
import com.xinyuan.ms.entity.SysPeriod;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.exception.BaseException;
import com.xinyuan.ms.mapper.PeriodRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.vo.AddPeriodVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeriodService extends BaseService<PeriodRepository, SysPeriod, Long> {

    @Autowired
    SysUserServiceImpl sysUserService;


    public List<SysPeriod> period() {
        List<SysPeriod> period = bizRepository.period();
        return period;
    }

    /**
     * 通过id查找周期
     * @param periodId
     * @return
     */
    public SysPeriod periodById(Long periodId) {
        SysPeriod period = bizRepository.periodById(periodId);
        return period;
    }

    public List<SysPeriod> allPeriod() {
        List<SysPeriod> period = bizRepository.periodList(null);
        return period;
    }

    public List<Ztree> periodTree() {
        List<SysPeriod> sysPeriods = bizRepository.periodList(null);
        return initZtree(sysPeriods);
    }


    public SysPeriod selectDeptByDeptId(Long periodId) {
        List<SysPeriod> sysPeriods = bizRepository.periodList(periodId);
        if (sysPeriods != null) {
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
    public List<Ztree> initZtree(List<SysPeriod> deptList) {
        return initZtree(deptList, null);
    }


    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysPeriod> deptList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = false;
        for (SysPeriod dept : deptList) {
            Ztree ztree = new Ztree();
            ztree.setId(dept.getPeriodId());
            ztree.setpId(dept.getParentId());
            ztree.setName(dept.getTitle());
            ztree.setTitle(dept.getTitle());
            ztree.setStartTime(dept.getStartTime());
            ztree.setEndTime(dept.getEndTime());
            if (isCheck) {
                ztree.setChecked(roleDeptList.contains(dept.getPeriodId() + dept.getTitle()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }


    /**
     * 新增考评周期
     * @param addPeriodVo
     */
    public void addPeriod(AddPeriodVo addPeriodVo) {
        SysPeriod sysPeriod = new SysPeriod();
        sysPeriod.setParentId(addPeriodVo.getPeriodId());
        sysPeriod.setTitle(addPeriodVo.getTitle());
        sysPeriod.setStartTime(getDate(addPeriodVo.getStartTime()));
        sysPeriod.setEndTime(getDate(addPeriodVo.getEndTime()));
        sysPeriod.setDelFlag("0");
        save(sysPeriod);
    }

    public Date getDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }


    /**
     * 删除用户
     */
    public int remove(Long id) throws BaseException {
        SysPeriod entity = bizRepository.findOne(id);
        if (entity != null) {
            if (ReflectionUtils.hasField(entity, "delFlag")) {
                ReflectionUtils.invokeSetter(entity, "delFlag", "1");
            }
            bizRepository.save(entity);
        }
        return 1;
    }

    public List<SysUserVo> getList(SysUser user) {
        List<SysUserVo> list = sysUserService.getList(user);
        return list;
    }
}
