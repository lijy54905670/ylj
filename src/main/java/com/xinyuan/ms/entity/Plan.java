package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author hzx
 * @Description:总计划完成情况
 * @date 2018/3/2519:59
 */
@Data
@Entity
@Table(name = "plan")
@ApiModel(value = "Plan", description = "计划任务完成情况对象Plan")
public class Plan {
    @Id
    @GeneratedValue()
    @ApiModelProperty(value = "主键ID", name = "id", example = "0")
    private Integer id;

    @Column(name = "plan_project")
    @ApiModelProperty(value = "所属项目", name = "project", example = "所属项目")
    private String planProject;

    @Column(name = "plan_name")
    @ApiModelProperty(value = "计划名称", name = "name", example = "计划名称")
    private String planName;

    @Column(name = "plan_principal")
    @ApiModelProperty(value = "负责人", name = "principal", example = "负责人")
    private String planPrincipal;

    @Column(name = "summarizing")
    @ApiModelProperty(value = "情况汇总", name = "summarizing", example = "情况汇总")
    private String summarizing;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注", name = "remarks", example = "备注")
    private String remarks;

    @Column(name = "log_id")
    @ApiModelProperty(value = "主表外键（工作日志）", name = "logId", example = "1")
    private Integer logId;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;
}
