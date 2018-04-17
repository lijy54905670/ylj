package com.xinyuan.ms.biz;


import com.xinyuan.ms.dao.WorkLogRepository;
import com.xinyuan.ms.entity.*;
import com.xinyuan.ms.exception.WorkLogException;
import com.xinyuan.ms.web.req.PageBody;
import com.xinyuan.ms.web.vo.MissionVo;
import com.xinyuan.ms.web.vo.PlanVo;
import com.xinyuan.ms.web.vo.WorkLogVo;
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
 * @Description:工作日志管理
 * @date 2018/3/2521:25
 */
@Service
public class WorkLogService extends BaseService<WorkLogRepository, WorkLog, Integer> {
    @Autowired
    private WorkLogRepository workLogRepository;


    @Autowired
    private PlanService planService;

    @Autowired
    private MissionService missionService;


    /**
     * id查询工作日志
     * @param id
     * @return
     */
    public WorkLogVo getWorkLogVo(int id) {
        WorkLog workLog = workLogRepository.findByIdAndDeleted(id, 0);
        WorkLogVo workLogVo = new WorkLogVo();
        if (workLog != null) {
            BeanUtils.copyProperties(workLog, workLogVo);
            List<PlanVo> planVoList = planService.getAllPlanVo(workLogVo.getId());
            List<MissionVo> missionVoList = missionService.getAllMissionVo(workLog.getId());
            workLogVo.setPlanList(planVoList);
            workLogVo.setMissionList(missionVoList);
        }
        return workLogVo;
    }


    /**
     * 查询所有工作日志和对应的计划工作情况和工作情况
     *
     * @param pageable
     * @param selectParams
     * @param hasRole
     * @return
     */
    public Page<WorkLogVo> findByPage(Pageable pageable, List<SelectParam> selectParams, Boolean hasRole) {

        Page<WorkLogVo> returnValue;
        Page page;
        if (pageable != null) {
            page = findByCondition(pageable.getPageNumber(), pageable.getPageSize(), selectParams);
        } else {
            page = findByCondition(0, 0, selectParams);
        }
        List<WorkLog> workLogList = page.getContent();
        returnValue = new PageImpl(workLogTransfer(workLogList, hasRole), pageable, page.getTotalElements());

        return returnValue;
    }

    public List<WorkLogVo> workLogTransfer(List<WorkLog> workLogList, Boolean hasRole) {
        List<WorkLogVo> returnValue = new ArrayList<>();

        if (!CollectionUtils.isEmpty(workLogList)) {
            for (WorkLog workLog : workLogList) {
                returnValue.add(workLogTransfer(workLog, hasRole));
            }
        }
        return returnValue;
    }

    public WorkLogVo workLogTransfer(WorkLog workLog, Boolean hasRole) {
        WorkLogVo workLogVo = new WorkLogVo();
        if (workLog != null) {
            BeanUtils.copyProperties(workLog, workLogVo);
            workLogVo.setPlanList(planService.getAllPlanVo(workLogVo.getId()));
            workLogVo.setMissionList(missionService.getAllMissionVo(workLog.getId()));
        }
        return workLogVo;

    }


    /**
     * 新增工作日志
     *
     * @param workLogVo
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public WorkLogVo addWorkLog(WorkLogVo workLogVo) {
        WorkLog workLog = new WorkLog();
        workLogVo.setDeleted(0);
        BeanUtils.copyProperties(workLogVo, workLog);
        workLog = this.save(workLog);
        List<MissionVo> missionVoList = workLogVo.getMissionList();
        if (missionVoList != null) {
            for (MissionVo missionVo : missionVoList) {
                missionService.addMission(missionVo, workLog.getId());
            }
        }
        List<PlanVo> planVoList = workLogVo.getPlanList();
        if (planVoList != null) {
            for (PlanVo planVo : planVoList) {
                planService.addPlan(planVo, workLog.getId());
            }
        }
        return workLogVo;
    }


    /**
     * 修改工作日志
     *
     * @param workLogVo
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public WorkLogVo updateWorkLog(WorkLogVo workLogVo) {
        WorkLog workLog = new WorkLog();
        BeanUtils.copyProperties(workLogVo, workLog);
        this.update(workLog);
        List<MissionVo> missionVoList = workLogVo.getMissionList();
        if (missionVoList != null) {
            for (MissionVo missionVo : missionVoList) {
                missionService.updateMission(missionVo, workLog.getId());
            }
        }
        List<PlanVo> planVoList = workLogVo.getPlanList();
        if (planVoList != null) {
            for (PlanVo planVo : planVoList) {
                planService.updatePlan(planVo, workLog.getId());
            }
        }
        return workLogVo;
    }


    /**
     * 通用条件查询（查多表）
     *
     * @param pageBody
     * @return
     */
    public Page<WorkLogVo> getAllWorkLogPage(PageBody pageBody) {
        Page<WorkLogVo> page = null;
        if (pageBody.getPageBean() != null) {
            page = findByPage(new PageBean(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize()), getSelectParamList(pageBody.getConditions()), false);
        } else {
            page = findByPage(null, getSelectParamList(pageBody.getConditions()), false);
        }
        return page;
    }



    /**
     * 异常处理name名字不存在的情况
     *
     * @param name
     * @throws Exception
     */
    @Transactional(rollbackOn = Exception.class)
    public void selectException(String name) {
        if (workLogRepository.findByNameAndAndDeleted(name, 0).size() < 1) {
            throw WorkLogException.WORK_LOG_NAME_REPEAT;
        }
    }
}
