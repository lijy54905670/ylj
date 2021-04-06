package com.xinyuan.ms.mapper;
import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;


public interface SysMenuRepository extends BaseJpaRepository<SysMenu,Long>{
    @Query(value = "select * from sys_menu where parent_id = '0' or parent_id = '11' or parent_id = '12' or parent_id = '13'",nativeQuery = true)
    List<SysMenu> SelectMenuAll();

    @Query(value = "select sm.* from sys_menu sm left join sys_role_menu srm on srm.menu_id = sm.menu_id left join sys_user_role sur on sur.role_id = srm.role_id where sur.user_id = ?",nativeQuery = true)
    List<SysMenu> SelectMenuAll(Long userId);

    @Query(value = "select menu_id from sys_role_menu where role_id = ?",nativeQuery = true)
    List<String> ids(Long rId);


}
