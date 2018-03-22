package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.biz.TaskService;
import com.xinyuan.ms.entity.Task;
import com.xinyuan.ms.web.resp.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author liang
 *
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/save")
    public ObjectRestResponse<Task> save(Task task){
        ObjectRestResponse returnValue = new ObjectRestResponse();

        returnValue.setData(taskService.save(task));

        return returnValue;
    }



}
