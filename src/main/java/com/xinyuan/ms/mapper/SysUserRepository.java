package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserRepository extends BaseJpaRepository<SysUser,Long>{

    @Query(value = "select u.user_id, u.dept_id, u.login_name, u.user_name, u.user_type, u.email, u.avatar, u.phonenumber, u.password, u.sex, u.salt, u.status, u.del_flag, u.login_ip, u.login_date, u.create_by, u.create_time, u.remark, d.dept_name, d.leader from sys_user u left join sys_dept d on u.dept_id = d.dept_id where u.del_flag = '0'" +
            "and if(:deptId != '',u.dept_id = :deptId,1=1)",nativeQuery = true)
    public List<SysUser> selectUserList(@Param("deptId")Long deptId);

    /**
     * 根据用户id查询用户信息
     * */
    @Query(value = "select * from sys_user where user_id = ?",nativeQuery = true)
    public SysUser selectUserByid(Long id);
}
