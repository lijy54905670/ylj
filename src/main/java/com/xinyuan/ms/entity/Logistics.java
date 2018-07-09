package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**

 * @author hwz
 */
@Data
@Entity
@Table(name = "m_logistics")
public class Logistics implements Serializable {

    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ApiModelProperty(value = "订单id", name = "orderId", example = "1")
    @Column(name = "order_id")
    private Long orderId;

    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "快递公司", name = "express", example = "哪都通")
    @Column(name = "express")
    private String express;

    @ApiModelProperty(value = "快递单号", name = "number", example = "66566666")
    @Column(name = "number", unique = true)
    private String number;

    @ApiModelProperty(value = "状态", name = "status", example = "1")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "时间", name = "time", example = "2018-04-12 10:55:23")
    @Column(name = "time",columnDefinition="timestamp")
    private String time;

    @ApiModelProperty(value = "删除标识 0未删除 1已删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted = 0;
}
