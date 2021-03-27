package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysDept;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptRepository extends BaseJpaRepository<SysDept, Long> {

    @Query(value = "select * from sys_dept",nativeQuery = true)
    public List<SysDept> selectDeptList();

    @Query(value = "select * from sys_dept where dept_id = ?",nativeQuery = true)
    public SysDept selectDeptByDeptId(Long deptId);

    @Query(value = "select d.dept_id, d.parent_id, d.ancestors, d.dept_name, d.order_num, d.leader, d.phone, d.email, d.status, d.del_flag, d.create_by, d.create_time \n" +
            "from sys_dept d  where d.del_flag = '0'",nativeQuery = true)
    public List<SysDept> selectDeptLists();
}
