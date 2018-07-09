package com.xinyuan.ms.mapper;
import com.xinyuan.ms.entity.PermissionRole;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author hwz
 */
@Repository
public interface PermissionRoleRepository extends BaseJpaRepository<PermissionRole,Long> {

    /**
     * rid
     * @param rid
     * @param deleted
     * @return
     */
    List<PermissionRole> findByRidAndDeleted(Long rid, int deleted);

}
