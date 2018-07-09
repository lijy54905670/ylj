package com.xinyuan.ms.mapper;
import com.xinyuan.ms.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author hwz
 */
@Repository
public interface PermissionRepository extends BaseJpaRepository<Permission,Long> {

    /**
     * findByDeleted
     * @param deleted
     * @return
     */
    List<Permission> findByDeleted(int deleted);


    /**
     * findByIdAndDeleted
     * @param id
     * @param deleted
     * @return
     */

    Permission findByIdAndDeleted(Long id, int deleted);

    /**
     * findByPidAndDeleted
     * @param pid
     * @param deleted
     * @return
     */
    List<Permission> findByPidAndDeleted(Long pid, int deleted);


}
