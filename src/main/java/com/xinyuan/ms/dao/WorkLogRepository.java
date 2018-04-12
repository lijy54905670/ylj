package com.xinyuan.ms.dao;


import com.xinyuan.ms.entity.WorkLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hzx
 * @Description:工作日志管理
 * @date 2018/3/2520:59
 */
@Repository
public interface WorkLogRepository extends BaseJpaRepository<WorkLog,Integer>{

    /**
     * 根据ID和Deleted标识查询WorkLog对象
     * @param id
     * @param deleted
     * @return mission对象
     */
    public WorkLog findByIdAndDeleted(int id, int deleted);

    /**
     * 根据名字查询WorkLog
     * @param name
     * @param deleted
     * @return
     */
    public List<WorkLog> findByNameAndAndDeleted(String name, int deleted);

}
