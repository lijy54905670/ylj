package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysTarget;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TargetRepository extends BaseJpaRepository<SysTarget,Long> {


    @Query(value = "select * from sys_target where del_flag = '0'",nativeQuery = true)
    public List<SysTarget> targetList();
}
