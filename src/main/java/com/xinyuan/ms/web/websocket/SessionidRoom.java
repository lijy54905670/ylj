package com.xinyuan.ms.web.websocket;

import lombok.Data;

import javax.websocket.Session;

/**
 * Created by 王亚飞 on 2018/4/19.
 */
@Data
public class SessionidRoom {


    private Long userid;

    private Long roomId;

    private Session session;



}
