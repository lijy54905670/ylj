package com.xinyuan.ms.service.impl;


import com.xinyuan.ms.entity.SysPeriodTarget;
import com.xinyuan.ms.mapper.SysPeriodTargetRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

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
}
