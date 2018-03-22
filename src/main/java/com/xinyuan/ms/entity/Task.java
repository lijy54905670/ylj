package com.xinyuan.ms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue()
    private int id;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "content")
    private String content;


}
