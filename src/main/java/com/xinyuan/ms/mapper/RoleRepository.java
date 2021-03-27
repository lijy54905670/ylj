package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysRole;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends BaseJpaRepository<SysRole,Long> {

    @Query(value = "select distinct r.role_id, r.role_name, r.role_key, r.role_sort, r.data_scope,\n" +
            "            r.status, r.del_flag, r.create_time, r.remark \n" +
            "        from sys_role r\n" +
            "\t        left join sys_user_role ur on ur.role_id = r.role_id\n" +
            "\t        left join sys_user u on u.user_id = ur.user_id\n" +
            "\t        left join sys_dept d on u.dept_id = d.dept_id",nativeQuery = true)
    public void roleList();
}
