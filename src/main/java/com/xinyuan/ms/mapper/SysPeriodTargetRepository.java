package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysPeriodTarget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysPeriodTargetRepository extends BaseJpaRepository<SysPeriodTarget,Long>{

    @Query(value ="select * from sys_period_target where if(:periodId != '',period_id = :periodId,1=1)" ,nativeQuery = true)
    public List<SysPeriodTarget> periodTarget(@Param("periodId") Long periodId);

}
