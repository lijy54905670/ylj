package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.common.entity.AjaxResult;
import com.xinyuan.ms.entity.SysPeriod;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.PeriodService;
import com.xinyuan.ms.web.vo.AddPeriodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/system/period")
public class PeriodController extends BaseController{
    String prefix = "period";

    @Autowired
    PeriodService periodService;

    @GetMapping
    public String period(){
        return prefix + "/period";
    }


    /**
     * 考评周期列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo periodList(){
        List<SysPeriod> period = periodService.period();
         return getDataTable(period);
    }

    @RequestMapping("/allList")
    @ResponseBody
    public List<SysPeriod> allPeriodList(){
        List<SysPeriod> period = periodService.allPeriod();
        return period;
    }

    @GetMapping("/periodEdit")
    public String periodEdit(){
        return prefix + "/periodEdit";
    }


    /**
     * 添加考评周期
     * @param periodId
     * @param model
     * @return
     */
    @GetMapping("/addPeriod/{id}")
    public String addPeriodHtml(@PathVariable("id") Long periodId,Model model){
        model.addAttribute("periodId",periodId);
        return prefix + "/addPeriod";
    }

    @PostMapping("/addPeriod")
    @ResponseBody
    public AjaxResult addPeriod(AddPeriodVo addPeriodVo){
        SysPeriod sysPeriod = new SysPeriod();
        periodService.addPeriod(addPeriodVo);
        return toAjax(1);
    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult removePeriod(@PathVariable("id") Long periodId){
        int remove = periodService.remove(periodId);
        return toAjax(remove);
    }


    /**
     * 返回指标分类树
     * @return
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = periodService.periodTree();
        return ztrees;
    }


    /**
     * 查询部门树
     * @param excludeId
     * @param mmap
     * @return
     */
    @GetMapping(value = { "/selectPeriodTree/{periodId}", "/selectPeriodTree/{periodId}/{excludeId}" })
    public String selectPeriodTree(@PathVariable("periodId") Long periodId,
                                   @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap) {
        mmap.put("period",periodService.selectDeptByDeptId(periodId));
        mmap.put("excludeId", excludeId);
        return "period/tree";
    }


    /**
     * 考评周期添加考评对象
     * @param periodId
     * @param model
     * @return
     */
    @GetMapping("/addPeriodUser/{id}")
    public String addPeriodUser(@PathVariable("id") Long periodId, Model model){
        model.addAttribute("periodId",periodId);
        return prefix + "/addPeriodUser";
    }



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
