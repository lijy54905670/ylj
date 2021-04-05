package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysUserPeriod;
import com.xinyuan.ms.service.impl.SysUserPeriodService;
import com.xinyuan.ms.web.request.RemoveRequest;
import com.xinyuan.ms.web.request.TargetVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/userPeriod")
public class SysUserPeriodController extends BaseController{

    @Autowired
    SysUserPeriodService sysUserPeriodService;


    /**
     * 添加考评对象
     * @param sysUserPeriod
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addUserPeriod(SysUserPeriod sysUserPeriod){
        System.out.println(sysUserPeriod.getUserIds());
        sysUserPeriodService.saveUserPeriod(sysUserPeriod);
        return toAjax(1);
    }


    /**
     * 查看考评周期所有的考评对象
     * @return
     */

    @PostMapping("/periodUser")
    @ResponseBody
    public TableDataInfo periodUser(TargetVo targetVo){
        List<SysUserVo> list = sysUserPeriodService.periodUser(targetVo);
        return getDataTable(list);
    }

    /**
     * 删除考评对象
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public AjaxResult remove(RemoveRequest removeRequest){
        boolean remove = sysUserPeriodService.remove(removeRequest);
        if (remove){
            return toAjax(1);
        }else {
            return AjaxResult.error("删除失败");
        }

    }
}
