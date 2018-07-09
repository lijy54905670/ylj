package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liang
 */
@Repository
public interface UserRepository extends BaseJpaRepository<User,Long> {

    /**
     * 通过username查询
     * @param name
     * @return
     */
    User findByUsername(String name);

    /**
     * findById
     * @param id
     * @return
     */
    User findByIdAndDeleted(Long id, int deleted);

    List<User> findByIdInAndDeleted(List<Long> ids, int deleted);

    /**
     *
     * @param username
     * @param deleted
     * @return
     */
    User findByUsernameAndDeleted(String username, int deleted);

    List<User> findByNicknameLikeAndIdInAndDeleted(String nickname, List<Long> ids, int deleted);

}
