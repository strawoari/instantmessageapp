package com.heiyu.messaging.controller;

import com.heiyu.messaging.model.GroupChat;
import com.heiyu.messaging.model.User;
import com.heiyu.messaging.request.AddMembersRequest;
import com.heiyu.messaging.request.CreateGroupChatRequest;
import com.heiyu.messaging.request.DeleteMembersRequest;
import com.heiyu.messaging.service.FriendService;
import com.heiyu.messaging.service.GroupchatService;
import com.heiyu.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("groupChats")
public class GroupChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private GroupchatService groupchatService;

    @PostMapping("/create")
    public void createGroupChat(@RequestBody CreateGroupChatRequest createGroupChatRequest) throws Exception {
        User creator = this.userService.authenticate(createGroupChatRequest.getLoginToken());
        GroupChat groupChat = new GroupChat();
        groupChat.setCreatorUserId(creator.getId());
        groupChat.setDescription(createGroupChatRequest.getDescription());
        groupChat.setName(createGroupChatRequest.getName());
        groupChat.setCreateTime(new Date());

        this.groupchatService.createGroupChat(groupChat);

        List<Integer> list = createGroupChatRequest.getUserIds();
        List<User> friends = this.friendService.findFriends(creator.getId());
        for (int i=0; i<list.size(); i++) {
            User person = this.userService.findById(list.get(i));
            if (friends.contains(person)) {
                this.groupchatService.addMember(person.getId(),groupChat.getId());
            }
        }
    }

    @PostMapping("/addMembers")
    public void addMembers(@RequestBody AddMembersRequest addMembersRequest) throws Exception {
        User adder = this.userService.authenticate(addMembersRequest.getLoginToken());
        List<Integer> list = addMembersRequest.getUserIds();
        List<User> friends = this.friendService.findFriends(adder.getId());

        for (int i=0; i<list.size(); i++) {
            User person = this.userService.findById(list.get(i));
            if (friends.contains(person) && !this.groupchatService.isInGroup(person.getId(),
                    addMembersRequest.getGroupChatId())) {
                this.groupchatService.addMember(person.getId(), addMembersRequest.getGroupChatId());
            }
        }
    }

    @PostMapping("/deleteMembers")
    public void deleteMembers(@RequestBody DeleteMembersRequest deleteMembersRequest) throws Exception {
        User deleter = this.userService.authenticate(deleteMembersRequest.getLoginToken());
        List<Integer> list = deleteMembersRequest.getUserIds();
        List<User> friends = this.friendService.findFriends(deleter.getId());

        for (int i=0; i<list.size(); i++) {
            User person = this.userService.findById(list.get(i));
            if (friends.contains(person) && this.groupchatService.isInGroup(person.getId(),
                    deleteMembersRequest.getGroupChatId())) {
                this.groupchatService.deleteMember(person.getId(), deleteMembersRequest.getGroupChatId());
            }
        }
    }
}
