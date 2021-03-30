package com.xinyuan.ms.service.impl;


import com.xinyuan.ms.entity.SysPeriodTarget;
import com.xinyuan.ms.mapper.SysPeriodTargetRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPeriodTargetService extends BaseService<SysPeriodTargetRepository, SysPeriodTarget,Long> {


    public boolean addPeriodTarget(SysPeriodTarget sysPeriodTarget){
        SysPeriodTarget save = save(sysPeriodTarget);
        if (save != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 通过考核期间id获取设置的指标id
     * @param periodId
     * @return
     */
    public String selectTargetIds(Long periodId){
        List<SysPeriodTarget> sysPeriodTarget = bizRepository.periodTarget(periodId);
        if (sysPeriodTarget != null && sysPeriodTarget.size()!=0) {
            return sysPeriodTarget.get(0).getTargetIds();
        }else {
            return null;
        }
    }
}
