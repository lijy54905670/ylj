package com.xinyuan.ms.mapper;

import com.xinyuan.ms.common.core.domain.entity.SysMenu;
import com.xinyuan.ms.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysMenuRepository extends BaseJpaRepository<SysMenu,Long>{
    @Query(value = "select * from sys_menu where parent_id = '1'",nativeQuery = true)
    public List<SysMenu> SelectMenuAll();
}
