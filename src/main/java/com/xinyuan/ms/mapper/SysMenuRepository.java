package com.xinyuan.ms.mapper;
import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface SysMenuRepository extends BaseJpaRepository<SysMenu,Long>{
    @Query(value = "select * from sys_menu where parent_id = '0' or parent_id = '11' or parent_id = '12' or parent_id = '13'",nativeQuery = true)
    List<SysMenu> SelectMenuAll();
}
