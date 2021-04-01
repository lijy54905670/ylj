package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysPeriod;
import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.SysUserPeriod;
import com.xinyuan.ms.mapper.SysUserPeriodRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.request.TargetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserPeriodService extends BaseService<SysUserPeriodRepository,SysUserPeriod,Long> {

    @Autowired
    SysUserServiceImpl sysUserService;

    @Autowired
    PeriodService periodService;

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
     */

    public List<SysUser> periodUser(TargetVo targetVo){
        SysUserPeriod sysUserPeriod = bizRepository.selectUserPeriodById(targetVo.getPeriodId());
        List<SysUser> userByIds = new ArrayList<>();
        if (sysUserPeriod != null) {
            userByIds = sysUserService.getUserByIds(sysUserPeriod.getUserIds());
        }
        return userByIds;
    }

}
