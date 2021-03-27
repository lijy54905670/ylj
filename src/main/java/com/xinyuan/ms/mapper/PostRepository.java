package com.xinyuan.ms.mapper;


import com.xinyuan.ms.entity.SysPost;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends BaseJpaRepository<SysPost,Long> {

    @Query(value = " select post_id, post_code, post_name, post_sort, status, create_by, create_time, remark from sys_post",nativeQuery = true)
    public List<SysPost> postList();
}
