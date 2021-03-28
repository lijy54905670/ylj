package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysRole;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends BaseJpaRepository<SysRole,Long> {

    @Query(value = "select distinct r.role_id, r.role_name, r.role_key, r.role_sort, r.data_scope,\n" +
            "            r.status, r.del_flag, r.create_time, r.remark \n" +
            "        from sys_role r",nativeQuery = true)
    public List<SysRole> roleList();
}
