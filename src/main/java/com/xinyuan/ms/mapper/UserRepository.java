package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.SysUser;
import com.xinyuan.ms.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @author hzx
 * @date 2019/7/10 10:10
 */
@Repository
public interface UserRepository extends BaseJpaRepository<User,Long>{

    /**
     * 通过权限id连接查询用户信息
     */
    @Query(value = "select su.user_id,su.dept_id,su.login_name,su.user_name,su.phonenumber,su.email from sys_user su left join sys_user_role sur on su.user_id = sur.user_id where sur.role_id = ?",nativeQuery = true)
    List<SysUser> allocatedList(Long rId);

}
