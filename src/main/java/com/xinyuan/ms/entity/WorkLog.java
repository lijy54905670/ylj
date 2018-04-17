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
public class WorkLog {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    @Column(name = "name")
    private String name;

    @Column(name = "situation")
    private String situation;

    @Column(name = "deleted",columnDefinition = "INT default 0")
    private Integer deleted;

}
