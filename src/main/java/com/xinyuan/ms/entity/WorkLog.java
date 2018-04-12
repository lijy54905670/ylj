package com.xinyuan.ms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hzx
 * @Description:工作日志管理
 * @date 2018/3/2519:33
 */
@Data
@Entity
@Table(name = "work_log")
@ApiModel(value = "WorkLog", description = "工作日志对象WorkLog")
public class WorkLog {
    @Id
    @GeneratedValue()
    @ApiModelProperty(value = "ID", name = "id", example = "0")
    private Integer id;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "日期", name = "date", example = "2018-03-29 00:00:00")
    private Date date;

    @Column(name = "name")
    @ApiModelProperty(value = "名字", name = "name", example = "名字")
    private String name;

    @Column(name = "situation")
    @ApiModelProperty(value = "总体情况", name = "situation", example = "总体情况")
    private String situation;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    @ApiModelProperty(value = "删除标识", name = "deleted", example = "0")
    private Integer deleted;

}
