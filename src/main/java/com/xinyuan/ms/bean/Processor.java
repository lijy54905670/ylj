package com.xinyuan.ms.bean;

import com.xinyuan.ms.entity.Task;
import com.xinyuan.ms.executor.ExecutorServiceFactoryImpl;
import com.xinyuan.ms.service.TaskService;

import java.util.concurrent.ExecutorService;

/**
 * @author liang
 */
public abstract class Processor {

    protected ExecutorServiceFactoryImpl executorServiceFactory;

    protected ExecutorService executorService;

    protected TaskService taskService;


    public Processor(ExecutorServiceFactoryImpl executorServiceFactory,
                     ExecutorService executorService) {
        this.executorServiceFactory = executorServiceFactory;
        this.executorService = executorService;
    }

    /**
     * 任务的执行方法
     *
     * @param task
     * @return
     */
    public abstract boolean process(Task task);

}
