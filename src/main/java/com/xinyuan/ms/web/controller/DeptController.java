package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.service.impl.DeptServiceImpl;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    DeptServiceImpl deptService;

    /**
     * 跳转部门页面
     * @return
     */
    @GetMapping()
    public String dept(){
        return "dept/dept";
    }

    /**
     * 获取部门列表
     * @param dept
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept){
        List<SysDept> deptList = deptService.selectDeptLists();
        return deptList;
    }
}
