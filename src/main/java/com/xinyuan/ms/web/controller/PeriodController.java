package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.entity.SysPeriod;
import com.xinyuan.ms.entity.Ztree;
import com.xinyuan.ms.service.impl.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo periodList(){
        List<SysPeriod> period = periodService.period();
         return getDataTable(period);
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
                                   @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("period",periodService.selectDeptByDeptId(periodId));
        mmap.put("excludeId", excludeId);
        return "period/tree";
    }
}
