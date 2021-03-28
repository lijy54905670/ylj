package com.xinyuan.ms.web.controller;


import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysPeriodTarget;
import com.xinyuan.ms.service.impl.SysPeriodTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.xinyuan.ms.common.entity.AjaxResult.error;

@Controller

@RequestMapping("/system/periodTarget")
public class SysPeriodTargetController extends BaseController{

    @Autowired
    SysPeriodTargetService sysPeriodTargetService;

    @RequestMapping("/add")
    @ResponseBody
    public AjaxResult add(SysPeriodTarget sysPeriodTarget){
        if (!sysPeriodTargetService.addPeriodTarget(sysPeriodTarget)){
            return error("设置考评失败！");
        }
        return toAjax(1);
    }

}
