package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.biz.WorkLogService;
import com.xinyuan.ms.web.req.PageBody;
import com.xinyuan.ms.web.vo.WorkLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hzx
 * @Description:工作日志管理
 * @date 2018/3/2521:35
 */
@Api(description = "工作日志管理")
@RestController
@RequestMapping("/workLog")
public class WorkLogController {
    @Autowired
    private WorkLogService workLogService;


    @ApiOperation(value = "删除工作日志", notes = "根据id来指定删除工作日志")
    @ApiImplicitParam(name = "ids", value = "工作日志ID集合", required = true, dataType = "Long")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delWorkLog(@RequestBody List<Integer> ids) {
        try {
            for (Integer id : ids) {
                workLogService.remove(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }


    @ApiOperation(value = "修改工作日志", notes = "修改工作日志")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateWorkLog(@RequestBody WorkLogVo workLogVo) {
        try {
            workLogService.updateWorkLog(workLogVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }



    @ApiOperation(value = "id查询工作日志", notes = "根据url中id查询工作日志")
    @ApiImplicitParam(name = "id", value = "工作日志ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ResponseEntity<WorkLogVo> getWorkLogVo(@PathVariable(value = "id") Integer id) {
        WorkLogVo workLogVo = new WorkLogVo();
        try {
            workLogVo = workLogService.getWorkLogVo(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(workLogVo);
    }




    @ApiOperation(value = "新增工作日志", notes = "新增工作日志")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addAll(@RequestBody WorkLogVo workLogVo) {
        try {
            workLogService.addWorkLog(workLogVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }



    @ApiOperation(value = "通用条件查询所有的工作日志【可分页】", notes = "通用条件查询所有的工作日志【可分页】")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<Page<WorkLogVo>> getAllWorkLog(@RequestBody PageBody pageBody) {
        Page<WorkLogVo> page = null;
        try {
            page = workLogService.getAllWorkLogPage(pageBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(page);
    }

}
