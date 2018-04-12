package com.xinyuan.ms.dao;


import com.xinyuan.ms.entity.Plan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hzx
 * @Description:总计划完成情况
 * @date 2018/3/2521:05
 */
@Repository
public interface PlanRepository extends BaseJpaRepository<Plan,Integer> {

    /**
     * 根据ID和Deleted标识查询Plan对象
     * @param id
     * @param deleted
     * @return mission对象
     */
    public Plan findByIdAndDeleted(int id, int deleted);

    /**
     *根据日志管理外键和deleted标识查询所有plan
     * @param logId
     * @param deleted
     * @return
     */
    public List<Plan> findByLogIdAndDeleted(int logId, int deleted);


    /**
     * 根据所属项目属性查询总计划任务完成情况
     * @param project
     * @param deleted
     * @return
     */
    public Plan findByPlanProjectAndDeleted(String project, int deleted);
}
