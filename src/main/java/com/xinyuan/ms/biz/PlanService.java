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
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private WorkLogService workLogService;

    public Plan findOne(int id) {
        return planRepository.findByIdAndDeleted(id, 0);
    }

    /**
     * 根据id查询计划任务情况和所属工作日志
     *
     * @param id
     * @return
     */
    public PlanVo getPlanVo(int id) {
        Plan plan = planRepository.findByIdAndDeleted(id, 0);
        PlanVo planVo = new PlanVo();
        if (plan != null) {
            BeanUtils.copyProperties(plan, planVo);
            WorkLogVo workLogVo = workLogService.getWorkLogVo(plan.getLogId());
            planVo.setWorkLogVo(workLogVo);
        }
        return planVo;
    }


    /**
     * 根据所属项目属性查询总计划任务完成情况
     *
     * @param project
     * @return
     */
    public Plan findByProjectAndDeleted(String project) {
        return planRepository.findByPlanProjectAndDeleted(project, 0);
    }


    /**
     * 根据项目名称查询总计划任务完成情况和对应的工作日志
     *
     * @param project
     * @return
     */
    public PlanVo getPlanVo(String project) {
        Plan plan = planRepository.findByPlanProjectAndDeleted(project, 0);
        PlanVo planVo = new PlanVo();
        if (plan != null) {
            BeanUtils.copyProperties(plan, planVo);
            WorkLogVo workLog = workLogService.getWorkLogVo(plan.getLogId());
            planVo.setWorkLogVo(workLog);
        }
        return planVo;
    }


    /**
     * 分页查询所有计划和对应的工作日志
     *
     * @param pageable
     * @param selectParams
     * @param hasRole
     * @return
     */
    public Page<PlanVo> findByPage(Pageable pageable, List<SelectParam> selectParams, Boolean hasRole) {

        Page<PlanVo> returnValue;
        Page page;
        if (pageable != null) {
            page = findByCondition(pageable.getPageNumber(), pageable.getPageSize(), selectParams);
        } else {
            page = findByCondition(0, 0, selectParams);
        }
        List<Plan> planList = page.getContent();
        returnValue = new PageImpl(planTransfer(planList, hasRole), pageable, page.getTotalElements());

        return returnValue;
    }

    public List<PlanVo> planTransfer(List<Plan> planList, Boolean hasRole) {
        List<PlanVo> returnValue = new ArrayList<>();

        if (!CollectionUtils.isEmpty(planList)) {
            for (Plan plan : planList) {
                returnValue.add(planTransfer(plan, hasRole));
            }
        }
        return returnValue;
    }

    public PlanVo planTransfer(Plan plan, Boolean hasRole) {
        PlanVo returnValue = new PlanVo();
        if (plan != null) {
            BeanUtils.copyProperties(plan, returnValue);
            returnValue.setWorkLogVo(workLogService.getWorkLogVo(plan.getLogId()));
        }
        return returnValue;

    }

   /* *//**
     * 异常处理，判断所属项目是否存在
     *
     * @param plan
     *//*
    @Transactional(rollbackOn = Exception.class)
    public void addException(Plan plan) {
        if (findByProjectAndDeleted(plan.getPlanProject()) != null) {
            throw WorkLogException.WORK_LOG_IS_REPEAT;
        }
    }*/


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
     * 通用条件查询（查单表）
     * @param pageBody
     * @return
     */
    public Page<Plan> getPlanPage(PageBody pageBody) {
        Page<Plan> page = null;
        if (pageBody.getPageBean() != null) {
            page = findByCondition(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize(), getSelectParamList(pageBody.getConditions()));
        } else {
            page = findByCondition(0, 0, getSelectParamList(pageBody.getConditions()));
        }
        return page;
    }

    /**
     * 通用条件查询（查多表）
     * @param pageBody
     * @return
     */
    public Page<PlanVo> getAllPlanPage(PageBody pageBody) {
        Page<PlanVo> page = null;
        if (pageBody.getPageBean() != null) {
            page = findByPage(new PageBean(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize(), null), getSelectParamList(pageBody.getConditions()), false);
        } else {
            page = findByPage(null, getSelectParamList(pageBody.getConditions()), false);
        }
        return page;
    }



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
