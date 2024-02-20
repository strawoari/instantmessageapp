package com.heiyu.messaging.service;

import com.heiyu.messaging.DAO.MessageDAO;
import com.heiyu.messaging.model.Message;
import com.heiyu.messaging.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private FriendService friendService;

    @Autowired
    private GroupchatService groupChatService;

    @Autowired
    private MessageDAO messageDAO;

    private static final int LIMIT = 50;

    public List<Message> listMessages(User user, Integer userId, Integer groupChatId, int page) throws Exception {

        int start = 0;
        if (userId != null) {
            if (!this.friendService.areFriends(user.getId(), userId)) {
                throw new Exception(String.format("%d and %d are not friends", user.getId(), userId));
            }
            start = (page - 1) * LIMIT;
            return this.messageDAO.selectPrivateMessagesPriv(user.getId(), userId, start, LIMIT);
        } else if (groupChatId != null) {
            return this.messageDAO.selectGroupMessages(groupChatId, start, LIMIT);
        }
        return null;
    }


    public void sendMessage(Integer receiver, Integer groupchat, int sender, String content) {
        if (receiver!= null) {
            this.messageDAO.sendMessagesPriv(receiver,sender,content);
        } else {
            this.messageDAO.sendMessagesGroup(groupchat,sender,content);
        }
    }
}
