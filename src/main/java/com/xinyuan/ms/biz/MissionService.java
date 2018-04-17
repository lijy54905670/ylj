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
     * 新增和修改
     * @param missionVo
     * @param workLogId
     * @return
     */
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
