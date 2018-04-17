package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author hzx
 * @Description:任务完成情况
 * @date 2018/3/2520:31
 */
@Data
@Entity
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "classify")
    private Integer classify;

    @Column(name = "project")
    private String project;

    @Column(name = "principal")
    private String principal;

    @Column(name = "assign")
    private String assign;

    @Column(name = "matter")
    private String matter;

    @Column(name = "accomplish")
    private String accomplish;

    @Column(name = "workload")
    private Integer workload;

    @Column(name = "practical")
    private Integer practical;

    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    private Integer deleted;

}
