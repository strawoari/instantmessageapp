package com.heiyu.messaging.controller;

import java.util.ArrayList;
import java.util.List;

import com.heiyu.messaging.enums.FriendInvitationStatus;
import com.heiyu.messaging.model.FriendInvitation;
import com.heiyu.messaging.model.User;
import com.heiyu.messaging.request.AcceptFriendInvitationRequest;
import com.heiyu.messaging.request.AddFriendRequest;
import com.heiyu.messaging.response.AugmentedFriendInvitation;
import com.heiyu.messaging.service.FriendService;
import com.heiyu.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public void addFriend(@RequestBody AddFriendRequest addFriendRequest) throws Exception {
        User sender = this.userService.authenticate(addFriendRequest.getLoginToken());
        this.friendService.addFriend(sender, addFriendRequest.getUsername(), addFriendRequest.getMessage());

    }

    @GetMapping("/listPendingInvitations")
    public List<AugmentedFriendInvitation> listPendingFriendInvitations(@RequestParam String loginToken) throws Exception {
        User user = this.userService.authenticate(loginToken);
        List<FriendInvitation> friendInvitations = this.friendService.listPendingInvitations(user);
        List<AugmentedFriendInvitation> augmentedFriendInvitations = new ArrayList<>();
        for (FriendInvitation friendInvitation : friendInvitations) {
            AugmentedFriendInvitation augmentedFriendInvitation = new AugmentedFriendInvitation();
            augmentedFriendInvitation.setMessage(friendInvitation.getMessage());
            augmentedFriendInvitation.setCreateTime(friendInvitation.getCreateTime());
            augmentedFriendInvitation.setFriendInvitationStatus(friendInvitation.getFriendinvitationStatus());
            augmentedFriendInvitation.setReceiverNickname(user.getNickname());
            augmentedFriendInvitation.setReceiverUsername(user.getUsername());
            User sender = this.userService.findById(friendInvitation.getSenderUserId());
            augmentedFriendInvitation.setSenderNickname(sender.getNickname());
            augmentedFriendInvitation.setSenderUsername(sender.getUsername());
            augmentedFriendInvitation.setId(friendInvitation.getId());
            augmentedFriendInvitations.add(augmentedFriendInvitation);
        }
        return augmentedFriendInvitations;
    }

    @PostMapping("/accept")
    public void accept(@RequestBody AcceptFriendInvitationRequest acceptFriendInvitationRequest) throws Exception {
        //why not requestparam?
        User friend=this.userService.authenticate(acceptFriendInvitationRequest.getLoginToken());
        FriendInvitationStatus friendInvitationStatus = FriendInvitationStatus.ACCEPTED;
        this.friendService.update(friend, acceptFriendInvitationRequest.getFriendInvitationId(), friendInvitationStatus);
    }

    @PostMapping("/reject")
    public void reject(@RequestBody AcceptFriendInvitationRequest acceptFriendInvitationRequest) throws Exception {
        User user = this.userService.authenticate(acceptFriendInvitationRequest.getLoginToken());
        FriendInvitationStatus friendInvitationStatus = FriendInvitationStatus.REJECTED;
        this.friendService.update(user, acceptFriendInvitationRequest.getFriendInvitationId(), friendInvitationStatus);
    }

    @GetMapping("/listFriends")
    public List<User> listFriends(@RequestParam String loginToken) throws Exception {
        User user = this.userService.authenticate(loginToken);
        return this.friendService.findFriends(user.getId());
    }
}
