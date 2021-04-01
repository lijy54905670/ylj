package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.SysUserPeriod;
import org.springframework.data.jpa.repository.Query;

public interface SysUserPeriodRepository extends BaseJpaRepository<SysUserPeriod,Long>{


    @Query(value = "select * from sys_user_period where period_id = ?",nativeQuery = true)
    public SysUserPeriod selectUserPeriodById(Long id);


}
