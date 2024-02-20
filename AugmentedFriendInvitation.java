package com.heiyu.messaging.response;

import java.util.Date;

import com.heiyu.messaging.enums.FriendInvitationStatus;

public class AugmentedFriendInvitation {
    private int id;
    private String message;
    private Date createTime;
    private FriendInvitationStatus friendInvitationStatus;
    private String senderUsername;
    private String senderNickname;
    private String receiverUsername;
    private String receiverNickname;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public FriendInvitationStatus getFriendInvitationStatus() {
        return friendInvitationStatus;
    }

    public void setFriendInvitationStatus(FriendInvitationStatus friendInvitationStatus) {
        this.friendInvitationStatus = friendInvitationStatus;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
