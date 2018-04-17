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
public class Plan {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "plan_project")
    private String planProject;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "plan_principal")
    private String planPrincipal;

    @Column(name = "summarizing")
    private String summarizing;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    private Integer deleted;
}
