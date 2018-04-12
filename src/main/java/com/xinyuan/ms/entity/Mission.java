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
@ApiModel(value = "Mission", description = "任务完成情况对象Notice")
public class Mission {
    @Id
    @GeneratedValue()
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    private Integer id;

    @Column(name = "classify")
    @ApiModelProperty(value = "分类（1：计划内任务完成情况，2：主管安排\\参与的其他事务）", name = "classify", example = "1")
    private Integer classify;

    @Column(name = "project")
    @ApiModelProperty(value = "所属项目", name = "project", example = "所属项目")
    private String project;

    @Column(name = "principal")
    @ApiModelProperty(value = "负责人", name = "principal", example = "负责人")
    private String principal;

    @Column(name = "assign")
    @ApiModelProperty(value = "分派人", name = "assign", example = "分派人")
    private String assign;

    @Column(name = "matter")
    @ApiModelProperty(value = "事项描述", name = "matter", example = "事项描述")
    private String matter;

    @Column(name = "accomplish")
    @ApiModelProperty(value = "完成描述", name = "accomplish", example = "完成描述")
    private String accomplish;

    @Column(name = "workload")
    @ApiModelProperty(value = "工作量（小时）", name = "workload", example = "1")
    private Integer workload;

    @Column(name = "practical")
    @ApiModelProperty(value = "实际工作量（小时）", name = "practical", example = "1")
    private Integer practical;

    @Column(name = "log_id")
    @ApiModelProperty(value = "外键（工作日志）", name = "logId", example = "1")
    private Integer logId;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;

}
