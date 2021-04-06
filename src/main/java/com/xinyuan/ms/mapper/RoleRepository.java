package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends BaseJpaRepository<SysRole,Long> {

    @Query(value = "select distinct r.role_id, r.role_name, r.role_key, r.role_sort, r.data_scope,\n" +
            "            r.status, r.del_flag, r.create_time, r.remark \n" +
            "        from sys_role r",nativeQuery = true)
    public List<SysRole> roleList();

    @Query(value = "select distinct r.role_id, r.role_name, r.role_key, r.role_sort, r.data_scope,\n" +
            "            r.status, r.del_flag, r.create_time, r.remark \n" +
            "        from sys_role r where r.role_id = ?",nativeQuery = true)
    SysRole role(Long id);

    /**
     * 新增用户权限
     */
    @Modifying
    @Query(value = "insert into sys_user_role(role_id,user_id) values(?1,?2)",nativeQuery = true)
    int addSelectAll(Long rId,Long uId);

    /**
     * 删除用户权限
     */
    @Modifying
    @Query(value = "delete from sys_user_role where role_id = ?1 and user_id in ?2",nativeQuery = true)
    int deleteSelect(Long rId, Set<Long> uIds);

    @Modifying
    @Query(value = "delete from sys_role_menu where role_id = ?",nativeQuery = true)
    int deleteAllByRoleId(Long rId);

    @Modifying
    @Query(value = "insert into sys_role_menu(role_id,menu_id) values(?1,?2)",nativeQuery = true)
    int insertRole(Long rId,String menu_id);

}
