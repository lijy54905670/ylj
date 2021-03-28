package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.entity.SysTarget;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/target")
public class TargetController extends BaseController{
    String prefix = "target";

    @Autowired
    TargetService targetService;

    /**
     * 跳转指标页
     * @return
     */
    @GetMapping
    public String target(){
        return prefix + "/target";
    }

    /**
     * 获取指标列表
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo targetList(){
        List<SysTarget> sysTargets = targetService.targetList();
       return getDataTable(sysTargets);
    }

    /**
     * 返回指标分类树
     * @return
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = targetService.targeTree();
        return ztrees;
    }


    @PostMapping("/addPeriod")
    public String add(){
        return prefix + "/add";
    }

    @GetMapping("/addPeriod/{id}")
    public String add1(@PathVariable("id") String id, Model model){
        System.out.println(id);
        model.addAttribute("ids",id);
        return prefix + "/add";
    }




}
