package com.xinyuan.ms.biz;

import com.xinyuan.ms.dao.TaskRepository;
import com.xinyuan.ms.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liang
 */
@Service
public class TaskService extends BaseService<TaskRepository,Task,Integer> {

}
