package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.ChatRoom;
import org.springframework.stereotype.Repository;

/**
 * Created by 王亚飞 on 2018/5/16.
 */
@Repository
public interface ChatRoomRepository extends BaseJpaRepository<ChatRoom,Long> {


    ChatRoom findByIdAndDeleted(Long id, int deleted);

    ChatRoom findByRoomNameAndDeleted(String roomName, int deleted);


}
