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
@ApiModel(value = "计划任务完成情况对象PlanVo", description = "计划任务完成情况对象PlanVo")
public class PlanVo {

    @ApiModelProperty(value = "主键ID", name = "id", example = "0")
    private Integer id;

    @ApiModelProperty(value = "所属项目", name = "project", example = "xy_erp")
    private String planProject;

    @ApiModelProperty(value = "计划名称", name = "name", example = "计划名称")
    private String planName;

    @ApiModelProperty(value = "负责人", name = "principal", example = "张三")
    private String planPrincipal;

    @ApiModelProperty(value = "情况汇总", name = "summarizing", example = "情况汇总")
    private String summarizing;

    @ApiModelProperty(value = "备注", name = "remarks", example = "备注")
    private String remarks;

    @ApiModelProperty(value = "主表外键（工作日志）", name = "logId", example = "1")
    private Integer logId;

    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;

    @ApiModelProperty(value = "工作日志WorkLogVo对象", name = "WorkLogVo")
    private WorkLogVo workLogVo;
}
