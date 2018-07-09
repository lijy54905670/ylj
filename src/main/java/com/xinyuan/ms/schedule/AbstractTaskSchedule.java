package com.xinyuan.ms.schedule;


import com.xinyuan.ms.bean.Processor;
import com.xinyuan.ms.common.enums.TaskStatus;
import com.xinyuan.ms.entity.Task;
import com.xinyuan.ms.executor.ExecutorServiceFactoryImpl;
import com.xinyuan.ms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author liang
 */
public abstract class AbstractTaskSchedule {

    protected ExecutorServiceFactoryImpl executorServiceFactory;
    protected ExecutorService executorService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ApplicationContext applicationContext;

    protected void process(Processor processor, List<Task> tasks, TaskService taskService, ExecutorService executorService) {
        for (Task task : tasks) {
            taskService.updateStatus(task, TaskStatus.RUNNING);
            executorService.submit(() -> {
                try {
                    if (processor.process(task)) {
                        taskService.updateStatus(task, TaskStatus.SUCCESS);
                    } else {
                        taskService.updateStatus(task, TaskStatus.FAILED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    taskService.updateStatus(task, TaskStatus.FAILED);
                }
            });
        }
    }
}
