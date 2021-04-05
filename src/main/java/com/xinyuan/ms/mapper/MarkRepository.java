package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysMark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MarkRepository extends BaseJpaRepository<SysMark,Long> {

    @Query(value = "select * from sys_mark where if(:uId != '',period_id = :uId,1=1)",nativeQuery = true)
    List<SysMark> getTargetIdsByUserId(@Param("uId") Long userId);

    @Query(value = "select * from sys_mark where user_id = ?",nativeQuery = true)
    List<SysMark> calculateScore(Long userId);

    @Query(value = "select * from sys_mark where user_id = ?",nativeQuery = true)
    List<SysMark> showInfo(Long uId);
}
