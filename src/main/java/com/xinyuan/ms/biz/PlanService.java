package com.xinyuan.ms.biz;

import com.xinyuan.ms.dao.PlanRepository;
import com.xinyuan.ms.entity.*;
import com.xinyuan.ms.web.req.PageBody;
import com.xinyuan.ms.web.vo.MissionVo;
import com.xinyuan.ms.web.vo.PlanVo;
import com.xinyuan.ms.web.vo.WorkLogVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzx
 * @Description:总计划完成情况
 * @date 2018/3/2521:27
 */
@Service
public class PlanService extends BaseService<PlanRepository, Plan, Integer> {



    /**
     * 根据外键日志管理查询所有计划工作完成情况
     *
     * @param logId
     * @return
     */
    public List<PlanVo> getAllPlanVo(Integer logId) {
        List<PlanVo> planVoList = new ArrayList<>();
        List<SelectParam> selectParamList = new ArrayList<>();
        selectParamList.add(new SelectParam("logId", logId, ParamCondition.EQUAL));
        List<Plan> planList = findByCondition(selectParamList);
        for (Plan plan : planList) {
            PlanVo planVo = new PlanVo();
            BeanUtils.copyProperties(plan, planVo);
            planVoList.add(planVo);
        }
        return planVoList;
    }


    /**
     * 新增和修改
     * @param planVo
     * @param workLogId
     * @return
     */
    public Plan getPlan(PlanVo planVo, Integer workLogId){
        planVo.setLogId(workLogId);
        Plan plan = new Plan();
        BeanUtils.copyProperties(planVo, plan);
        return  plan;
    }

    public void updatePlan(PlanVo planVo, Integer workLogId){
        update(getPlan(planVo,workLogId));
    }

    public Plan addPlan(PlanVo planVo, Integer workLogId){
        return save(getPlan(planVo,workLogId));
    }


}
