package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.SysUserPeriod;
import com.xinyuan.ms.mapper.SysUserPeriodRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.request.TargetVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserPeriodService extends BaseService<SysUserPeriodRepository,SysUserPeriod,Long> {

    @Autowired
    SysUserServiceImpl sysUserService;

    @Autowired
    PeriodService periodService;


    @Autowired
    DeptServiceImpl deptService;

    @Autowired
    MarkService markService;

    /**
     * 保存userPeriod数据
     * @param sysUserPeriod
     */
    public void saveUserPeriod(SysUserPeriod sysUserPeriod){
        Long periodId = sysUserPeriod.getPeriodId();
        SysUserPeriod entity = null;
        if (periodId != null){
            entity = this.userPeriodById(periodId);
        }
        if (entity !=null){
            String userIds1 = entity.getUserIds();
            String userIds2 = sysUserPeriod.getUserIds();
            sysUserPeriod.setUserIds(userIds1 + "," +userIds2);
            sysUserPeriod.setId(entity.getId());
        }
        SysUserPeriod save = save(sysUserPeriod);
    }

    /**
     * 通过periodId查询是否存在记录
     * @param periodId
     * @return
     */
    public SysUserPeriod userPeriodById(Long periodId){
        SysUserPeriod sysUserPeriod = bizRepository.selectUserPeriodById(periodId);
        return sysUserPeriod;
    }

    /**
     * 返回考评期间所有用户
     *
     * @return
     */
    public List<SysUserVo> periodUser(TargetVo targetVo){
        SysUserPeriod sysUserPeriod = bizRepository.selectUserPeriodById(targetVo.getPeriodId());
        List<SysUser> userByIds = new ArrayList<>();
        List<SysUserVo> sysUserVos = new ArrayList<>();
        if (sysUserPeriod != null) {
            Set<Long> exceptIds = markService.exceptIds(targetVo.periodId);
            userByIds = sysUserService.getUserByIds(sysUserPeriod.getUserIds(),exceptIds);
            for (int i = 0; i < userByIds.size(); i++) {
                Long deptId= userByIds.get(i).getDeptId();
                Double aDouble = markService.calculateScore(userByIds.get(i).getUserId());
                SysDept sysDept = deptService.selectDeptByDeptId(deptId);
                SysUserVo sysUserVo = new SysUserVo();
                EntityUtils.copyPropertiesIgnoreNull(userByIds.get(i),sysUserVo);
                sysUserVo.setDept(sysDept);
                sysUserVo.setScore(aDouble);
                sysUserVos.add(sysUserVo);
            }
        }
        return sysUserVos;
    }

    public List<SysUserVo> periodUser2(TargetVo targetVo){
        SysUserPeriod sysUserPeriod = bizRepository.selectUserPeriodById(targetVo.getPeriodId());
        List<SysUser> userByIds = new ArrayList<>();
        List<SysUserVo> sysUserVos = new ArrayList<>();
        if (sysUserPeriod != null) {
            Set<Long> exceptIds = markService.exceptIds(targetVo.periodId);
            userByIds = sysUserService.getUserByIds(exceptIds.toString().replace("[","").replace("]","").replace(" ",""),new HashSet<>());
            for (int i = 0; i < userByIds.size(); i++) {
                Long deptId= userByIds.get(i).getDeptId();
                Double aDouble = markService.calculateScore(userByIds.get(i).getUserId());
                SysDept sysDept = deptService.selectDeptByDeptId(deptId);
                SysUserVo sysUserVo = new SysUserVo();
                EntityUtils.copyPropertiesIgnoreNull(userByIds.get(i),sysUserVo);
                sysUserVo.setDept(sysDept);
                sysUserVo.setScore(aDouble);
                sysUserVos.add(sysUserVo);
            }
        }
        return sysUserVos;
    }

}
