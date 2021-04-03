package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysTarget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TargetRepository extends BaseJpaRepository<SysTarget,Long> {


    @Query(value = "select * from sys_target where del_flag = '0'",nativeQuery = true)
    List<SysTarget> targetList();

    @Query(value = "select * from sys_target where del_flag = '0' and if(:flag != '',target_id in :ids,1=2)",nativeQuery = true)
    List<SysTarget> targetList1(@Param("flag") String flag,@Param("ids") Set<Long> ids);

    @Query(value = "select * from sys_target where target_id = ?",nativeQuery = true)
    SysTarget getTargetById(Long tId);
}
