package com.heiyu.messaging.DAO;

import com.heiyu.messaging.enums.FriendInvitationStatus;
import com.heiyu.messaging.model.FriendInvitation;
import com.heiyu.messaging.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface FriendInvitationDAO {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO friend_invitation (receiver_user_id, sender_user_id, message, status, " +
            "create_time) VALUES " + "(#{receiverUserId}, #{senderUserId}, #{message}, #{status}, #{createTime})")
    void insert(FriendInvitation friend);

    @Select("SELECT * from invitations WHERE receiver_user_id = #{receiverUserId} and status = 'PENDING'")
    List<FriendInvitation> showPInvitations(int receiverUserId);

    @Update("UPDATE invitations SET status = #{friendInvitationStatus} WHERE id=#{friendInvitationId}")
    void update(int friendInvitationId, FriendInvitationStatus friendInvitationStatus);

    @Select("SELECT sender_user_id from invitations WHERE receiver_user_id = #{id} and status = 'ACCEPTED'")
    List<FriendInvitation> findsenderfriends(int id);

    @Select("SELECT receiver_user_id from invitations WHERE sender_user_id = #{id} and status = 'ACCEPTED'")
    List<FriendInvitation> findreceiverfriends(int id);
}
