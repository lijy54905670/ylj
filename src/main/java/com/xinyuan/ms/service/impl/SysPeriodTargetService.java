package com.xinyuan.ms.service.impl;


import com.xinyuan.ms.entity.SysPeriodTarget;
import com.xinyuan.ms.mapper.SysPeriodTargetRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPeriodTargetService extends BaseService<SysPeriodTargetRepository, SysPeriodTarget,Long> {

    /**
     * 添加周期指标
     * @param sysPeriodTarget
     * @return
     */
    public boolean addPeriodTarget(SysPeriodTarget sysPeriodTarget){

        if (sysPeriodTarget.getPeriodId() != null) {
            Long periodId = sysPeriodTarget.getPeriodId();
            List<SysPeriodTarget> sysPeriodTargetByPeriodId = getSysPeriodTargetByPeriodId(periodId);
            if (sysPeriodTargetByPeriodId != null && sysPeriodTargetByPeriodId.size() != 0) {
                SysPeriodTarget periodTarget = sysPeriodTargetByPeriodId.get(0);
                sysPeriodTarget.setId(periodTarget.getId());
                sysPeriodTarget.setTargetIds(sysPeriodTarget.getTargetIds() + "," + periodTarget.getTargetIds());
            }
        }
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

    /**
     * 通过周期id查询记录
     * @return
     */
    public List<SysPeriodTarget> getSysPeriodTargetByPeriodId(Long periodId){
        List<SysPeriodTarget> sysPeriodTargets = bizRepository.periodTarget(periodId);
        return sysPeriodTargets;
    }

}
