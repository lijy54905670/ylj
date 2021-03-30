package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysPeriod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeriodRepository extends BaseJpaRepository<SysPeriod,Long>{


    @Query(value = "select * from sys_period where del_flag = '0' and if(:periodId != '',period_id = :periodId,1=1)",nativeQuery = true)
    public List<SysPeriod> periodList(@Param("periodId") Long periodId);

    @Query(value = "select * from sys_period where parent_id != 0 and del_flag = '0'",nativeQuery = true)
    public List<SysPeriod> period();

}
