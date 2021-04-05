package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysDept;
import com.xinyuan.ms.service.impl.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController{

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

    /**
     * 跳转部门添加页面
     * @return
     */
    @GetMapping("/add")
    public String addDept(){
        return "dept/add";
    }




    /**
     * 添加部门
     * @return
     */
    @RequestMapping("/add1")
    @ResponseBody
    public AjaxResult addDept(SysDept sysDept){
        deptService.addDept(sysDept);
        return toAjax(1);
    }


    /**
     * 修改部门
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        SysDept deptById = deptService.getDeptById(id);
        model.addAttribute("dept",deptById);
        return "dept/edit";
    }


    @RequestMapping("/edit")
    @ResponseBody
    public AjaxResult editDept(SysDept sysDept){
        deptService.updateDept(sysDept);
        return toAjax(1);
    }



}
