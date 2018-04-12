package com.xinyuan.ms.dao;

import com.xinyuan.ms.entity.Mission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hzx
 * @Description:任务完成情况
 * @date 2018/3/2521:06
 */
@Repository
public interface MissionRepository extends BaseJpaRepository<Mission,Integer> {

    /**
     * 根据ID和Deleted标识查询Mission对象
     * @param id
     * @param deleted
     * @return mission对象
     */
    public Mission findByIdAndDeleted(int id, int deleted);

    /**
     *根据日志管理外键和deleted标识查询所有plan
     * @param logId
     * @param deleted
     * @return
     */
    public List<Mission> findByLogIdAndDeleted(int logId, int deleted);


    /**
     * 根据任务完成情况分类查询任务类别
     * @param classify
     * @param deleted
     * @return
     */
    public List<Mission> findByClassifyAndDeleted(int classify, int deleted);

    /**
     * 根据所属项目属性查询任务完成情况
     * @param project
     * @param deleted
     * @return
     */
    public Mission findByProjectAndDeleted(String project, int deleted);
}
