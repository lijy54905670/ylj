package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.common.util.EntityUtils;
import com.xinyuan.ms.entity.SysMark;
import com.xinyuan.ms.entity.SysPeriodTarget;
import com.xinyuan.ms.entity.SysTarget;
import com.xinyuan.ms.mapper.MarkRepository;
import com.xinyuan.ms.service.BaseService;
import com.xinyuan.ms.web.request.TargetVo;
import com.xinyuan.ms.web.vo.MarkVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarkService extends BaseService<MarkRepository, SysMark,Long> {


    @Autowired
    SysUserPeriodService sysUserPeriodService;

    @Autowired
    SysPeriodTargetService sysPeriodTargetService;

    @Autowired
    TargetService targetService;

    /**
     * 通过periodId获取考评对象
     * @return
     */
    public List<SysUserVo> markList(TargetVo targetVo){
        List<SysUserVo> sysUsers = sysUserPeriodService.periodUser(targetVo);
        return sysUsers;
    }

    public List<SysUserVo> markList2(TargetVo targetVo){
        List<SysUserVo> sysUsers = sysUserPeriodService.periodUser2(targetVo);
        return sysUsers;
    }

    public List<MarkVo> targetList(String periodIds){
        String[] split = periodIds.split(",");
        List<SysPeriodTarget> sysPeriodTarget= sysPeriodTargetService.getSysPeriodTargetByPeriodId(Long.parseLong(split[1]));
        String targetIds = sysPeriodTarget.get(0).getTargetIds();
        Set<Long> targetIdList = Arrays.stream(targetIds.split(",")).map(Long::parseLong).collect(Collectors.toSet());
        List<SysTarget> sysTargets = targetService.targetList(targetIdList);

        List<MarkVo> markVos = new ArrayList<>();
        for (int i = 0; i < sysTargets.size(); i++) {
            MarkVo markVo  = new MarkVo();
            EntityUtils.copyPropertiesIgnoreNull(sysTargets.get(i),markVo);
            markVo.setUserId(Long.parseLong(split[0]));
            markVo.setPeriodId(Long.parseLong(split[1]));
            markVos.add(markVo);
        }
        return markVos;
    }

    public void saveMark(List<MarkVo> jsonValues){
        for (int i = 0; i < jsonValues.size(); i++) {
            SysMark sysMark = new SysMark();
            EntityUtils.copyPropertiesIgnoreNull(jsonValues.get(i),sysMark);
            save(sysMark);
        }
    }

    /**
     * 查询已打分user
     * @param pId
     * @return
     */
    public Set<Long> exceptIds(Long pId){
        List<SysMark> targetIdsByUserId = bizRepository.getTargetIdsByUserId(pId);
        Set<Long> exceptId = new HashSet<>();
        for (int i = 0; i < targetIdsByUserId.size(); i++) {
            exceptId.add(targetIdsByUserId.get(i).getUserId());
        }
        return exceptId;
    }

    /**
     * 计算用户分数
     */
    public Double calculateScore(Long userId){
        List<SysMark> sysMarks = bizRepository.calculateScore(userId);
        Double score = 0.0;
        int sum = 0;
        if (sysMarks != null &&sysMarks.size()>0) {
            sum = sysMarks.stream().collect(Collectors.summingInt(s -> s.getScore()));
            score = sum / (sysMarks.size() * 10.0)*100;
        }
        return score;
    }


    /**
     * 通过用户id查询用户评分详细
     * @return
     */
    public List<MarkVo> showInfo(Long uid){
        List<SysMark> sysMarks = bizRepository.showInfo(uid);
        List<MarkVo> markVos = new ArrayList<>();
        for (int i = 0; i < sysMarks.size(); i++) {
            MarkVo markVo = new MarkVo();
            EntityUtils.copyPropertiesIgnoreNull(sysMarks.get(i),markVo);
            SysTarget targetById = targetService.getTargetById(sysMarks.get(i).getTargetId());
            markVo.setContent(targetById.getContent());
            markVos.add(markVo);
        }
        return markVos;
    }
}
