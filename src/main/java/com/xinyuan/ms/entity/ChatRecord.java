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
@Table(name = "chat_record")
public class ChatRecord {
    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ApiModelProperty(value = "信息", name = "message", example = "1")
    @Column(name = "message",columnDefinition ="text")
    private String message;

    @ApiModelProperty(value = "谁发的", name = "fromId", example = "1")
    @Column(name = "fromId")
    private Long fromId;

    @ApiModelProperty(value = "消息状态", name = "status", example = "已读 1 未读2")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "发给谁", name = "toId", example = "1")
    @Column(name = "toid")
    private Long toid;

    @ApiModelProperty(value = "发送时间", name = "time", example = "1")
    @Column(name = "contentTime")
    private String contentTime;


    @ApiModelProperty(value = "信息类型", name = "contentType", example = "图片images 文字text,心跳")
    @Column(name = "contentType")
    private String contentType;


    @ApiModelProperty(value = "房间id", name = "roomId", example = "1")
    @Column(name = "roomId")
    private Long roomId;


    @ApiModelProperty(value = "删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted;
}
