package com.xinyuan.ms.web.vo;

import com.xinyuan.ms.entity.ChatRecord;
import com.xinyuan.ms.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 王亚飞 on 2018/5/17.
 */
@Data
public class ChatRecordUserVo {
    @ApiModelProperty(value = "这个房的聊天记录", name = "chatRoom")
    private ChatRecord chatRecord;
    @ApiModelProperty(value = "房间聊天发送的人的信息", name = "user")
    private User user;
    @ApiModelProperty(value = "房间聊天接受的人的信息", name = "users")
    private User users;



}
