package com.xinyuan.ms.web.request;


public class TargetVo {

    public Long periodId;

    public Long parentId;

    public String cla;

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "TargetVo{" +
                "targetId=" + periodId +
                ", parentId=" + parentId +
                '}';
    }
}
