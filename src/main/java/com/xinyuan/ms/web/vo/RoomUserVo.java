package com.xinyuan.ms.web.vo;

import lombok.Data;

import javax.websocket.Session;
import java.util.HashMap;

/**
 * Created by 王亚飞 on 2018/5/16.
 */
@Data
public class RoomUserVo {

    private Integer id;

    private String roomName;

   // private List<Integer>  userList=new ArrayList<Integer>();

    private Session session;

    private   HashMap<Object,Object> hashMap=new  HashMap<Object,Object>();


}
