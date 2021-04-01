package com.xinyuan.ms.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sys_user_period")
public class SysUserPeriod {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userIds;

    private Long periodId;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }



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



    @Override
    public String toString() {
        return "SysUserPeriod{" +
                "id=" + id +
                ", userId=" + userIds +
                ", periodId=" + periodId +
                '}';
    }
}
