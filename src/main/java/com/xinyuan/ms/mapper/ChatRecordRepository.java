package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.ChatRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王亚飞 on 2018/5/15.
 */
@Repository
public interface ChatRecordRepository extends BaseJpaRepository<ChatRecord, Long> {


    List<ChatRecord> findByRoomIdAndStatusAndDeleted(Long roomId, Integer status, int deleted);

    List<ChatRecord> findByRoomIdAndStatusAndDeletedOrderByContentTimeDesc(Long roomId, Integer status, int deleted);

    List<ChatRecord> findByRoomIdAndDeletedOrderByContentTimeDesc(Long roomId, int deleted);


    ChatRecord save(ChatRecord chatRecord);

}
