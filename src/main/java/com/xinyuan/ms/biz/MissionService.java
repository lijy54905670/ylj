package com.xinyuan.ms.biz;


import com.xinyuan.ms.dao.MissionRepository;
import com.xinyuan.ms.entity.*;
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
 * @Description:任务完成情况
 * @date 2018/3/2521:34
 */
@Service
public class MissionService extends BaseService<MissionRepository, Mission, Integer> {
    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private WorkLogService workLogService;

    /**
     * 根据id查询任务完成情况对象
     *
     * @param id
     * @return
     */
    public Mission findOne(int id) {
        return missionRepository.findByIdAndDeleted(id, 0);
    }

    /**
     * 连表查询完成任务和对应的工作日志
     *
     * @param id
     * @return
     */
    public MissionVo getMissionVo(int id) {
        Mission mission = missionRepository.findByIdAndDeleted(id, 0);
        MissionVo missionVo = new MissionVo();
        if (mission != null) {
            BeanUtils.copyProperties(mission, missionVo);
            WorkLogVo workLogVo = workLogService.getWorkLogVo(mission.getLogId());
            missionVo.setWorkLogVo(workLogVo);
        }
        return missionVo;
    }

    /**
     * 根据分类查询任务
     *
     * @param classify
     * @return
     */
    public List<Mission> findByClassifyAndDeleted(int classify) {
        return missionRepository.findByClassifyAndDeleted(classify, 0);
    }


    /**
     * 根据分类查询所有的任务和对应的日志
     *
     * @param missionList
     * @return
     */
    public Page<MissionVo> findAllMissionVo(Integer pageNum, Integer pageSize, List<Mission> missionList) {
        List<MissionVo> missionVoList = new ArrayList<>();
        if (missionList != null) {
            for (Mission x : missionList) {
                MissionVo missionVo = new MissionVo();
                BeanUtils.copyProperties(x, missionVo);
                WorkLogVo workLogVo = workLogService.getWorkLogVo(x.getLogId());
                missionVo.setWorkLogVo(workLogVo);
                missionVoList.add(missionVo);
            }
        }
        PageUtil<MissionVo> pageUtil = new PageUtil<MissionVo>(pageNum, pageSize, missionVoList);
        return pageUtil;
    }


    public Page<MissionVo> findByPage(Pageable pageable, List<SelectParam> selectParams, Boolean hasRole) {

        Page<MissionVo> returnValue;
        Page page;
        if (pageable != null) {
            page = findByCondition(pageable.getPageNumber(), pageable.getPageSize(), selectParams);
        } else {
            page = findByCondition(0, 0, selectParams);
        }
        List<Mission> workLogList = page.getContent();
        returnValue = new PageImpl(missionTransfer(workLogList, hasRole), pageable, page.getTotalElements());

        return returnValue;
    }

    public List<MissionVo> missionTransfer(List<Mission> planList, Boolean hasRole) {
        List<MissionVo> returnValue = new ArrayList<>();

        if (!CollectionUtils.isEmpty(planList)) {
            for (Mission mission : planList) {
                returnValue.add(missionTransfer(mission, hasRole));
            }
        }
        return returnValue;
    }

    public MissionVo missionTransfer(Mission mission, Boolean hasRole) {
        MissionVo returnValue = new MissionVo();
        if (mission != null) {
            BeanUtils.copyProperties(mission, returnValue);
            returnValue.setWorkLogVo(workLogService.getWorkLogVo(mission.getLogId()));
        }
        return returnValue;

    }


    /**
     * 根据所属项目属性查询任务完成情况
     *
     * @param project
     * @return
     */
    public Mission findByProjectAndDeleted(String project) {
        return missionRepository.findByProjectAndDeleted(project, 0);
    }


    /**
     * 根据项目名称查询任务完成情况和对应的工作日志
     *
     * @param project
     * @return
     */
    public MissionVo getMissionVo(String project) {
        Mission mission = missionRepository.findByProjectAndDeleted(project, 0);
        MissionVo missionVo = new MissionVo();
        if (mission != null) {
            BeanUtils.copyProperties(mission, missionVo);
            WorkLogVo workLogVo = workLogService.getWorkLogVo(mission.getLogId());
            missionVo.setWorkLogVo(workLogVo);
        }
        return missionVo;
    }

    /**
     * 异常处理
     *
     * @param mission
     */
   /* @Transactional(rollbackOn = Exception.class)
    public void addException(Mission mission) {
        if (findByProjectAndDeleted(mission.getProject()) != null) {
            throw WorkLogException.WORK_LOG_IS_REPEAT;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void selectException(Integer id) {
        if (findOne(id) == null) {
            throw WorkLogException.WORK_LOG_ID_REPEAT;
        }
    }*/


    /**
     * 根据外键日志管理查询所有工作完成情况
     *
     * @param logId
     * @return
     */
    public List<MissionVo> getAllMissionVo(Integer logId) {
        List<MissionVo> missionVoList = new ArrayList<>();
        List<SelectParam> selectParamList = new ArrayList<>();
        selectParamList.add(new SelectParam("logId", logId, ParamCondition.EQUAL));
        List<Mission> missionList = findByCondition(selectParamList);
        for (Mission mission : missionList) {
            MissionVo missionVo = new MissionVo();
            BeanUtils.copyProperties(mission, missionVo);
            missionVoList.add(missionVo);
        }
        return missionVoList;
    }


    /**
     * 通用条件查询（查单表）
     * @param pageBody
     * @return
     */
    public Page<Mission> getMissionPage(PageBody pageBody) {
        Page<Mission> page = null;
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
    public Page<MissionVo> getAllMissionPage(PageBody pageBody) {
        Page<MissionVo> page = null;
        if (pageBody.getPageBean() != null) {
            Mission mission;
            page = findByPage(new PageBean(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize(), null), getSelectParamList(pageBody.getConditions()), false);
        } else {
            page = findByPage(null, getSelectParamList(pageBody.getConditions()), false);
        }
        return page;
    }


    public Mission getMission(MissionVo missionVo,Integer workLogId){
        missionVo.setLogId(workLogId);
        Mission mission = new Mission();
        BeanUtils.copyProperties(missionVo, mission);
        return  mission;
    }


    public void updateMission(MissionVo missionVo, Integer workLogId){
        update(getMission(missionVo,workLogId));
    }

    public Mission addMission(MissionVo missionVo, Integer workLogId){
       return save(getMission(missionVo,workLogId));
    }

}
