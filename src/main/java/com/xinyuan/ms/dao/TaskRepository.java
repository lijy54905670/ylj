package com.xinyuan.ms.dao;

import com.xinyuan.ms.entity.Task;
import org.springframework.stereotype.Repository;

/**
 * @author liang
 */
@Repository
public interface TaskRepository extends  BaseJpaRepository<Task,Integer> {



}
