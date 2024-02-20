package com.heiyu.messaging.service;

import com.heiyu.messaging.DAO.GroupChatDAO;
import com.heiyu.messaging.model.GroupChat;
import com.heiyu.messaging.model.GroupMember;
import com.heiyu.messaging.request.CreateGroupChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupchatService {
    @Autowired
    private GroupChatDAO groupChatDAO;
    public void createGroupChat(GroupChat group) {
        this.groupChatDAO.makeGroup(group.getName(),group.getCreatorUserId(),
                group.getDescription(),group.getCreateTime());
    }

    public void addMember(int userId, int groupchatId) {
        this.groupChatDAO.addMember(userId, groupchatId);
    }
    public void deleteMember(int userId, int groupChatId) {
        this.groupChatDAO.deleteMember(userId, groupChatId);
    }
    public boolean isInGroup(int userId, Integer groupchatId) {
        List<GroupMember> groups = this.groupChatDAO.findbyUser(userId, groupchatId);
        return !groups.isEmpty();
    }
}
