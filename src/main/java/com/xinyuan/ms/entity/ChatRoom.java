package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 王亚飞 on 2018/5/15.
 */
@Data
@Entity
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ApiModelProperty(value = "房间名", name = "roomName", example = "xixi")
    @Column(name = "roomName",columnDefinition ="text")
    private String roomName;


    @ApiModelProperty(value = "房间时间", name = "roomTime", example = "房间时间")
    @Column(name = "roomTime")
    private String roomTime;

    @ApiModelProperty(value = "删除", name = "deleted", example = "0 1 ")
    @Column(name = "deleted")
    private Integer deleted;

   /* @ApiModelProperty(value = "商品id", name = "goodId", example = "1")
    @Column(name = "goodId")
    private Integer goodId;*/


}
