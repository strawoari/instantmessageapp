package com.heiyu.messaging.DAO;

import com.heiyu.messaging.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDAO {
    @Select("SELECT * FROM message WHERE (receiver_user_id = #{userIdA} AND sender_user_id = #{userIdB}) OR " +
            "(receiver_user_id = #{userIdB} AND sender_user_id = #{userIdA}) " +
            "order by send_time desc limit #{start}, #{limit}")
    List<Message> selectPrivateMessagesPriv(int userIdA, Integer userIdB, int start, int limit);
    @Select("SELECT * FROM message WHERE (group_chat_id = #{groupId}) " +
            "order by send_time desc limit #{start}, #{limit}")
    List<Message> selectGroupMessages(int groupId, int start, int limit);

    @Insert("INSERT INTO messages (group_chat_id, sender_id, content) " +
            "VALUES (#{groupchat}, #{sender}, #{content})")
    void sendMessagesGroup(Integer groupchat, int sender, String content);
    @Insert("INSERT INTO messages (receiver_user_id, sender_user_id, content) " +
            "VALUES (#{receiver}, #{sender}, #{content})")
    void sendMessagesPriv(Integer receiver, int sender, String content);


}