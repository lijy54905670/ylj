package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.entity.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/userPeriod")
public class SysUserPeriodController extends BaseController{


    @GetMapping("/add/{id}")
    @ResponseBody
    public AjaxResult addUserPeriod(@PathVariable("id") String periodId){
        System.out.println(periodId);
        return toAjax(1);
    }
}
