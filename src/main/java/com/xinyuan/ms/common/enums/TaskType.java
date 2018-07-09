// Copyright 2016 Baidu Inc. All rights reserved.

package com.xinyuan.ms.common.enums;

/**
 * The task type.
 *
 * @author liang
 */
public enum TaskType {

    RS_IMPORT_TASK("位点数据导入任务"),

    GENE_IMPORT_TASK("儿童基因数据导入任务"),

    COMPUTER_TASK("成人计算任务"),

    CHILD_COMPUTER_TASK("儿童计算任务"),

    REPORT_TASK("报告生成任务"),

    HTML_REPORT_TASK("报告生成任务"),

    Mall_TASK("商城任务"),

    TAKE_TASK("商城任务"),

    SEND_TASK("商城任务");


    private String name;

    TaskType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TaskType parse(String name) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getName().equals(name)) {
                return taskType;
            }
        }
        return null;
    }
}
