package com.xinyuan.ms.entity;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sys_mark")
public class SysMark {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long id;

    public Long periodId;

    public Long targetId;

    public Long userId;

    public Integer score;

    public Integer weight;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SysMark{" +
                "id=" + id +
                ", periodId=" + periodId +
                ", targetId=" + targetId +
                ", userId=" + userId +
                ", score=" + score +
                ", weight=" + weight +
                '}';
    }
}
