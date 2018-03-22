package com.xinyuan.ms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liang
 */
@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue()
    private int id;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @Column(name = "file")
    private String file;

    @Column(name = "refer_id")
    private int referId;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "version")
    private int version;

}
