package com.heiyu.messaging.service;

import java.util.*;

import com.heiyu.messaging.DAO.FriendInvitationDAO;
import com.heiyu.messaging.enums.FriendInvitationStatus;
import com.heiyu.messaging.model.FriendInvitation;
import com.heiyu.messaging.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendInvitationDAO friendInvitationDAO;

    public void addFriend(User sender, String receiverUsername, String message) throws Exception {
        User receiver = this.userService.findByUsername(receiverUsername);

        // validation: sender and receiver have not been friends yet
        // validation: sender and receiver are not the same user
        // validation: sender and receiver have had an pending invitation

        FriendInvitation friendInvitation = new FriendInvitation();
        friendInvitation.setSenderUserId(sender.getId());
        friendInvitation.setReceiverUserId(receiver.getId());
        friendInvitation.setMessage(message);
        friendInvitation.setFriendinvitationStatus(FriendInvitationStatus.PENDING);
        friendInvitation.setCreateTime(new Date());

        this.friendInvitationDAO.insert(friendInvitation);
        //send acceptFriendInvitationRequest (friendInvitation.getId(), senderLoginToken)
    }

    public List<FriendInvitation> listPendingInvitations(User user) {
        return this.friendInvitationDAO.showPInvitations(user.getId());
    }

    public void update(User friend, int friendInvitationId, FriendInvitationStatus friendInvitationStatus) throws Exception {
        this.friendInvitationDAO.update(friendInvitationId, friendInvitationStatus);
    }

    public List<User> findFriends(int Id) throws Exception {
        List<User> friends = new ArrayList<>();
        List<FriendInvitation> acceptedInv1 = this.friendInvitationDAO.findsenderfriends(Id);
        for (int i=0; i<acceptedInv1.size(); i++) {
            int friendId = acceptedInv1.get(i).getSenderUserId();
            User friend = this.userService.findById(friendId);
            friends.add(friend);
        }
        List<FriendInvitation> acceptedInv2 = this.friendInvitationDAO.findreceiverfriends(Id);
        for (int i=0; i<acceptedInv2.size(); i++) {
            int friendId = acceptedInv2.get(i).getReceiverUserId();
            User friend = this.userService.findById(friendId);
            friends.add(friend);
        }
        return friends;
    }
    public boolean areFriends(Integer userIdA, Integer userIdB) {
        List list1 = this.friendInvitationDAO.findreceiverfriends(userIdA);
        if (list1.contains(userIdB)) {
            return true;
        }
        List list2 = this.friendInvitationDAO.findsenderfriends(userIdA);
        if (list2.contains(userIdB)) {
            return true;
        }
        return false;
    }

}
