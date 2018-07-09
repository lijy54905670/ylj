package com.xinyuan.ms.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 任务对象
 *
 * @author liang
 */
@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Long id;


    @Column(name = "type")
    @ApiModelProperty(value = "任务类型", name = "name", example = "REPORT")
    private String type;

    @Column(name = "content")
    @ApiModelProperty(value = "任务内容", name = "name", example = "任务内容，显示文件名、样本ID")
    private String content;

    @Column(name = "status")
    @ApiModelProperty(value = "任务状态", name = "name", example = "SUCCESS','RUNNING','FAILED','SUBMITTED")
    private String status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "任务创建时间", name = "name", example = "2018-01-01")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "任务更新时间", name = "name", example = "2018-01-01")
    private Date updateTime;

    @Column(name = "version")
    @ApiModelProperty(value = "计算版本号", name = "name", example = "4")
    private Integer version;

    @Column(name = "deleted",columnDefinition = "INT DEFAULT 0")
    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;
}
