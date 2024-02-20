package com.heiyu.messaging.DAO;

import com.heiyu.messaging.enums.FriendInvitationStatus;
import com.heiyu.messaging.model.FriendInvitation;
import com.heiyu.messaging.model.GroupMember;
import com.heiyu.messaging.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface GroupChatDAO {
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO groups (receiver_user_id, sender_user_id, message, status, " +
            "create_time) VALUES " + "(#{receiverUserId}, #{senderUserId}, #{message}, #{status}, #{createTime})")
    void makeGroup(String name, Integer creatorUserId, String description, Date createTime);

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO group_chat_member (user_id, group_chat_id) VALUES (#{user_id}, #{groupchatId})")
    void addMember(int userId, int groupchatId);

    @Delete("DELETE FROM group_chat_member WHERE user_id = #{userId} and group_chat_id = #{groupChatId}")
    void deleteMember(int userId, int groupChatId);
    @Select("SELECT * from group_chat_member WHERE user_id = #{userId} and groupchatId = #{group_chat_id}")
    List<GroupMember> findbyUser(int userId, int groupchatId);

    @Update("UPDATE invitations SET status = #{friendInvitationStatus} WHERE id=#{friendInvitationId}")
    void update(int friendInvitationId, FriendInvitationStatus friendInvitationStatus);



}
