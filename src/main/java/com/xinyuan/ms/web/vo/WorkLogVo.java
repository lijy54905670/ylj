package com.xinyuan.ms.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hzx
 * @Description:工作日志
 * @date 2018/3/2710:48
 */
@Data
@ApiModel(value = "工作日志对象WorkLogVo", description = "工作日志对象WorkLogVo")
public class WorkLogVo {
    @ApiModelProperty(value = "ID", name = "id", example = "0")
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "日期", name = "date", example = "2018-4-9 00:00:00")
    private Date date;

    @ApiModelProperty(value = "名字", name = "name", example = "张三")
    private String name;

    @ApiModelProperty(value = "总体情况", name = "situation", example = "总体情况")
    private String situation;

    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;

    @ApiModelProperty(value = "从表（总计划任务情况）", name = "planList")
    List<PlanVo> planList=new ArrayList<>();

    @ApiModelProperty(value = "从表（任务完成情况）", name = "missionList")
    List<MissionVo> missionList=new ArrayList<>();


}
