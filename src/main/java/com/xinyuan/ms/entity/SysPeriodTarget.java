package com.xinyuan.ms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sys_period_target")
public class SysPeriodTarget {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long periodId;

    private String targetIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(String targetIds) {
        this.targetIds = targetIds;
    }

    @Override
    public String toString() {
        return "SysPeriodTarget{" +
                "id=" + id +
                ", periodId=" + periodId +
                ", targetIds='" + targetIds + '\'' +
                '}';
    }
}
