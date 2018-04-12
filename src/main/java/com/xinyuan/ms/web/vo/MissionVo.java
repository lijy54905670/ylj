package com.xinyuan.ms.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzx
 * @Description:
 * @date 2018/3/2711:57
 */
@Data
@ApiModel(value = "任务完成情况对象MissionVo", description = "任务完成情况对象MissionVo")
public class MissionVo {

    @ApiModelProperty(value = "主键", name = "id", example = "0")
    private Integer id;

    @ApiModelProperty(value = "分类（1：计划内任务完成情况，2：主管安排\\参与的其他事务）", name = "classify", example = "1")
    private Integer classify;

    @ApiModelProperty(value = "所属项目", name = "project", example = "xy_erp")
    private String project;

    @ApiModelProperty(value = "负责人", name = "principal", example = "张三")
    private String principal;

    @ApiModelProperty(value = "分派人", name = "assign", example = "李四")
    private String assign;

    @ApiModelProperty(value = "事项描述", name = "matter", example = "空")
    private String matter;

    @ApiModelProperty(value = "完成描述", name = "accomplish", example = "空")
    private String accomplish;

    @ApiModelProperty(value = "工作量（小时）", name = "workload", example = "1")
    private Integer workload;

    @ApiModelProperty(value = "实际工作量（小时）", name = "practical", example = "1")
    private Integer practical;

    @ApiModelProperty(value = "外键（工作日志）", name = "logId", example = "1")
    private Integer logId;

    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;

    @ApiModelProperty(value = "主表对象（工作日志）", name = "workLogVo")
    private WorkLogVo workLogVo;
}
