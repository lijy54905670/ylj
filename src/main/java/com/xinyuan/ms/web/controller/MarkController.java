package com.xinyuan.ms.web.controller;


import com.alibaba.fastjson.JSON;
import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.service.impl.MarkService;
import com.xinyuan.ms.web.request.TargetVo;
import com.xinyuan.ms.web.vo.MarkVo;
import com.xinyuan.ms.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/system/mark")
public class MarkController extends  BaseController{


    @Autowired
    MarkService markService;

    String prefix = "mark";

    /**
     * 跳转mark页面
     * @param session
     * @return
     */
    @GetMapping()
    public String markHtml(HttpSession session){
        return prefix + "/mark";
    }

    /**
     * 获取对应考核周期考核对象
     * @param targetVo
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo markList(TargetVo targetVo){
        List<SysUserVo> sysUsers = markService.markList(targetVo);

        return getDataTable(sysUsers);
    }

    @RequestMapping("/list1")
    @ResponseBody
    public TableDataInfo markList1(TargetVo targetVo){
        List<SysUserVo> sysUsers = markService.markList2(targetVo);

        return getDataTable(sysUsers);
    }

    /**
     * 跳转打分页面
     */
    @GetMapping("/mark/{id}")
    public String periodMark(@PathVariable("id") String userId, Model model){
        model.addAttribute("uId",userId);
        return "mark/periodMark";
    }

    @RequestMapping("/targetList")
    @ResponseBody
    public TableDataInfo targetList(String periodId){
        List<MarkVo> markVos = markService.targetList(periodId);
        return getDataTable(markVos);
    }

    @RequestMapping("/addMark")
    @ResponseBody
    public AjaxResult addMark(@RequestParam(name = "sysMarks") String sysMarks){
        System.out.println(sysMarks);
        ArrayList arrayList = JSON.parseObject(sysMarks, ArrayList.class);

        List<MarkVo> markVos = JSON.parseArray(sysMarks, MarkVo.class);
        markService.saveMark(markVos);
        return toAjax(1);
    }

    /**
     * 跳转显示结果查询页面
     * @return
     */
    @GetMapping("/showMark")
    public String showMark(){
        return  prefix + "/showMark";
    }

    /**
     * 跳转结果详细页面
     */

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Long  id,Model model){
        model.addAttribute("uId",id);
        return prefix + "/detail";
    }


    /**
     * 显示打分详细页
     * @return
     */
    @RequestMapping("/markInfo")
    @ResponseBody
    public TableDataInfo showInfo(TargetVo targetVo){
        List<MarkVo> markVos = markService.showInfo(targetVo.getPeriodId());
        return getDataTable(markVos);
    }
}
