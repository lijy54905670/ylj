package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/** 用户客服房间中间表
 * Created by 王亚飞 on 2018/5/15.
 */
@Data
@Entity
@Table(name = "user_room")
public class UserRoom {
    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户ID", name = "userId", example = "0")
    private Long userId;

    @Column(name = "otherId")
    @ApiModelProperty(value = "其他ID", name = "otherId", example = "客服id，医生id")
    private Long otherId;


    @Column(name = "roomId")
    @ApiModelProperty(value = "房间id", name = "roomId", example = "0")
    private Long roomId;


    @Column(name = "goodId")
    @ApiModelProperty(value = "哪个商品或病症", name = "goodId", example = "0")
    private Long goodId;

    @ApiModelProperty(value = "聊天类型", name = "userType", example = "商城客服1 医患在线2 遗传咨询3 体检咨询4")
    @Column(name = "userType")
    private Integer userType;

    @ApiModelProperty(value = "删除", name = "deleted", example = "0 1 ")
    @Column(name = "deleted")
    private Integer deleted;

}
