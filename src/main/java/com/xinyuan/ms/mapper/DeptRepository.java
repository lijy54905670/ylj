package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysDept;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptRepository extends BaseJpaRepository<SysDept, Long> {

    @Query(value = "select * from sys_dept",nativeQuery = true)
    public List<SysDept> selectDeptList();

    @Query(value = "select * from sys_dept where dept_id = ?",nativeQuery = true)
    public SysDept selectDeptByDeptId(Long deptId);
}
