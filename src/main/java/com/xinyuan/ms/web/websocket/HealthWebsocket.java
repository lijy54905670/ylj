
package com.xinyuan.ms.web.websocket;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyuan.ms.common.util.ApplicationContextRegister;
import com.xinyuan.ms.entity.ChatRecord;
import com.xinyuan.ms.entity.ChatRoom;
import com.xinyuan.ms.mapper.ChatRoomRepository;
import com.xinyuan.ms.service.ChatRecordService;
import com.xinyuan.ms.web.vo.RoomUserVo;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


/**医患咨询
 * Created by 王亚飞 on 2018/5/16.
 */

@ServerEndpoint(value = "/healthwebsocket/{userid}/{roomName}")
@Component
public class HealthWebsocket {
    //@Autowired
    private ChatRecordService chatRecordService;

    // @Autowired
    private ChatRoomRepository chatRoomRepository;

    //房间名 和房间
    public static Map<String,RoomUserVo> roomMap= Collections.synchronizedMap(new HashMap<String,RoomUserVo>()) ;
    //用户id 房间id session绑定一起
    private static List<SessionidRoom> sessionidRoomList=new ArrayList<SessionidRoom>();

    /**
     *
     *
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userid") Long userid,@PathParam("roomName") String roomName) {

        //房间
       // System.out.println("进来-------------------------------------------------");
        //拿到mapper
        ApplicationContext act= ApplicationContextRegister.getApplicationContext();
        chatRoomRepository= act.getBean(ChatRoomRepository.class);
        chatRecordService= act.getBean(ChatRecordService.class);
        ChatRoom chatRoom=chatRoomRepository.findByRoomNameAndDeleted(roomName,0);
        RoomUserVo roomUserVo=roomMap.get(roomName);
        if(roomUserVo==null){
            roomUserVo=new RoomUserVo();
        }
        roomUserVo.getHashMap().put(userid+roomName,session);
        roomMap.put(roomName,roomUserVo);

        //方便删除
        SessionidRoom sessionidRoom=new SessionidRoom();
        sessionidRoom.setUserid(userid);
        sessionidRoom.setRoomId(chatRoom.getId());
        sessionidRoom.setSession(session);
        sessionidRoomList.add(sessionidRoom);


    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws Exception{
        //把前台的json信息转成java
        JSONObject chat = JSON.parseObject(message);
        //是谁发送的 userid
        JSONObject jsonObject=JSON.parseObject(chat.get("data").toString());
        //发送的信息类型 图片 文字
        String contentType=jsonObject.get("contentType").toString();
        if(contentType.equals("HeartBeat")){
            HashMap hashMap=new HashMap<String,Object>();
            hashMap.put("contentType","HeartBeat");
            // HashMap hashMap1=new HashMap<String,Object>();
            // hashMap1.put("data",hashMap);
            sendMessage(session, JSONUtils.toJSONString(hashMap));
        }else{
            Long fromId=Long.valueOf(jsonObject.get("fromId").toString());
            //发给谁的
            Long toId=Long.valueOf(jsonObject.get("toId").toString());
            //发送的信息
            String messages=jsonObject.get("message").toString();
            //房间名字
            String roomName=jsonObject.get("roomName").toString();


            ChatRoom chatRoom=chatRoomRepository.findByRoomNameAndDeleted(roomName,0);
            RoomUserVo roomUserVo=roomMap.get(roomName);
            HashMap<Object,Object> hashMap=roomUserVo.getHashMap();
            Session session1=(Session) hashMap.get(fromId+roomName);
            Session session2=(Session) hashMap.get(toId+roomName);

            //说明俩人都在,消息都是已读状态
            if(session1!=null&&session2!=null){
                sendCommonRecord(message,session1,session2,chat,fromId,messages,roomName,toId,contentType,chatRoom.getId());
            }else{
                //说明有一个人不在
                //接收者不在,则设置为未读
                sendCommonRecords(message,session1,session2,chat,fromId,messages,roomName,toId,contentType,chatRoom.getId());
            }
        }
        //System.out.println("来自客户端的消息:" + message);
    }



    public void sendMessage(Session session,String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    //给聊天的双发发共同消息,已读
    private  void sendCommonRecord(String message,Session session1,Session session2,JSONObject chat,Long fromId,String messages,String roomName,Long toId,String contentType,Long roomid)throws Exception {
        //是谁发送的 userid
        JSONObject jsonObject=JSON.parseObject(chat.get("data").toString());
       // System.out.println(chat.get("data").toString());
        String times=new Timestamp(System.currentTimeMillis()).toString();
        HashMap hashMap=new HashMap<String,Object>();
        hashMap.put("fromId",fromId);
        hashMap.put("toId",toId);
        hashMap.put("message",messages);
        hashMap.put("roomName",roomName);
        hashMap.put("contentType",contentType);
        hashMap.put("contentTime",times);
        //HashMap hashMap1=new HashMap<String,Object>();
        //hashMap1.put("data",hashMap);

        session1.getBasicRemote().sendText(JSONUtils.toJSONString(hashMap));
        ChatRecord chatRecord=new ChatRecord();
        chatRecord.setDeleted(0);
        chatRecord.setFromId(fromId);
        chatRecord.setToid(toId);
        chatRecord.setMessage((String)jsonObject.get("message"));
        chatRecord.setContentType(contentType);
        chatRecord.setRoomId(roomid);
        chatRecord.setStatus(1);
        chatRecord.setContentTime(times);
        chatRecordService.addchatRecord(chatRecord);
        session2.getBasicRemote().sendText(JSONUtils.toJSONString(hashMap));
    }

    //发送聊天信息，未读状态
    private  void sendCommonRecords(String message,Session session1,Session session2,JSONObject chat,Long fromId,String messages,String roomName,Long toId,String contentType,Long roomid) throws Exception{
        JSONObject jsonObject=JSON.parseObject(chat.get("data").toString());
        String times=new Timestamp(System.currentTimeMillis()).toString();
        HashMap hashMap=new HashMap<String,Object>();
        hashMap.put("fromId",fromId);
        hashMap.put("toId",toId);
        hashMap.put("message",messages);
        hashMap.put("roomName",roomName);
        hashMap.put("contentType",contentType);
        hashMap.put("contentTime",times);
        //  HashMap hashMap1=new HashMap<String,Object>();
        //  hashMap1.put("data",hashMap);
        if(session1==null&&session2!=null){
            session2.getBasicRemote().sendText(JSONUtils.toJSONString(hashMap));
        }else{
            session1.getBasicRemote().sendText(JSONUtils.toJSONString(hashMap));
        }
        //将这条聊天记录保存在数据库
        ChatRecord chatRecord=new ChatRecord();
        chatRecord.setDeleted(0);
        chatRecord.setFromId(fromId);
        chatRecord.setToid(toId);
        chatRecord.setMessage((String)jsonObject.get("message"));
        chatRecord.setContentType(contentType);
        chatRecord.setRoomId(roomid);
        chatRecord.setStatus(0);
        chatRecord.setContentTime(times);
        chatRecordService.addchatRecord(chatRecord);
    }



    /**
     * 对特定用户发送消息
     * @param message
     * @param session
     */
    public void singleSend(String message, Session session){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        for(SessionidRoom s:sessionidRoomList){
            if(session.getId().equals(s.getSession().getId())){
                ChatRoom chatRoom=chatRoomRepository.findByIdAndDeleted(s.getRoomId(),0);
                RoomUserVo roomUserVo=roomMap.get(chatRoom.getRoomName());
                roomUserVo.getHashMap().remove(s.getUserid()+chatRoom.getRoomName());
                // hashMap.remove(s.getUserid()+chatRoom.getRoomName());
                roomMap.put(chatRoom.getRoomName(),roomUserVo);
                if(roomUserVo.getHashMap().size()==0){
                    roomMap.remove(chatRoom.getRoomName());
                }
              //  System.out.println(roomMap.size());
                sessionidRoomList.remove(s);
              //  System.out.println(sessionidRoomList.toString());
                return ;
            }
        }

    }

}

