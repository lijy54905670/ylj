package com.xinyuan.ms.service;

import com.xinyuan.ms.common.service.Order;
import com.xinyuan.ms.common.service.PageBean;
import com.xinyuan.ms.common.service.SelectParam;
import com.xinyuan.ms.common.web.Conditions;
import com.xinyuan.ms.common.web.PageBody;
import com.xinyuan.ms.entity.ChatRecord;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.mapper.ChatRecordRepository;
import com.xinyuan.ms.web.vo.ChatRecordUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王亚飞 on 2018/5/15.
 */
@Service
public class ChatRecordService extends BaseService<ChatRecordRepository, ChatRecord, Long> {

    @Autowired
    private ChatRecordRepository chatRecordRepository;


    @Autowired
    private UserService userService;



   public  List<ChatRecord> findByRoomIdAndStatusAndDeletedOrderByContentTimeDesc(Long roomId,Integer status){
        return chatRecordRepository.findByRoomIdAndStatusAndDeletedOrderByContentTimeDesc(roomId,status,0);
    };


    public void addchatRecord(ChatRecord chatRecord){
        chatRecordRepository.save(chatRecord);
    }

    public  List<ChatRecord> findByRoomIdOrderByContentTimeDesc(Long roomId){

        return chatRecordRepository.findByRoomIdAndDeletedOrderByContentTimeDesc(roomId,0);
    };

    //根据房间id和状态 查询所有未读消息
    List<ChatRecord> findByRoomIdAndStatus(Long roomId,Integer status){
        return chatRecordRepository.findByRoomIdAndStatusAndDeleted(roomId,status,0);
    }


    @Transactional(rollbackFor = Exception.class)
    public void removes(List<Integer> ids){
        for(Integer i:ids){
            super.remove(new Long(i.longValue()));
        }
    }


    //消息记录分页
    public Page recordShow(PageBody pageBody) {
        Page page;
        Sort sort = sortss(pageBody.getOrder());
        List<Conditions> conditionsList=pageBody.getConditions();
        Conditions conditions=conditionsList.get(1);
        if(conditions.getValue().equals("toDate")){
            String times=new Timestamp(System.currentTimeMillis()).toString();
            conditions.setValue(times);
        }
        if (pageBody.getPageBean() != null) {
            page = findRecord(new PageBean(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize(),sort), getSelectParamList(pageBody.getConditions()), false);
        } else {
            page = findRecord(new PageBean(1, Integer.MAX_VALUE,sort), getSelectParamList(pageBody.getConditions()), false);
        }
        return page;
    }

    public Page findRecord(Pageable pageable, List<SelectParam> selectParams, Boolean hasRole) {

        Page returnValue;
        Page page = findByCondition(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort(), selectParams);
        List<ChatRecord> chatList = page.getContent();
        returnValue = new PageImpl(answerShowTransfer(chatList, hasRole), pageable, page.getTotalElements());

        return returnValue;
    }


    public List<ChatRecordUserVo> answerShowTransfer(List<ChatRecord> chatList, Boolean hasRole) {
        List<ChatRecordUserVo> returnValue = new ArrayList<>();
        if (!CollectionUtils.isEmpty(chatList)) {
            for (ChatRecord chat : chatList) {
                chat.setStatus(1);
                super.update(chat);
                returnValue.add(answerShowTransfers(chat, hasRole));
            }
        }
        return returnValue;
    }


    public ChatRecordUserVo answerShowTransfers(ChatRecord chat, Boolean hasRole) {
        ChatRecordUserVo chatRecordUserVo=new ChatRecordUserVo();
        chatRecordUserVo.setChatRecord(chat);
       // ChatRoom chatRoom=chatRoomRepository.findById(chat.getRoomId());
        User user=userService.findById((long)chat.getFromId());
        chatRecordUserVo.setUser(user);
        User users=userService.findById((long)chat.getToid());
        chatRecordUserVo.setUsers(users);

        return chatRecordUserVo;

    }


    public Sort sortss(Order order) {
        Sort sort ;
        if (order != null) {
            if (("DESC").equals(order.getDirection())) {
                sort = new Sort(Sort.Direction.DESC, order.getProperties());
            } else {
                sort = new Sort(Sort.Direction.ASC, order.getProperties());
            }
        } else {
            sort = new Sort(Sort.Direction.ASC, "contentTime");
        }
        return sort;
    }



}
