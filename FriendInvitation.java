package com.heiyu.messaging.model;

import java.util.Date;

import com.heiyu.messaging.enums.FriendInvitationStatus;

public class FriendInvitation {

    private int id;
    private int receiverUserId;
    private int senderUserId;
    private String message;
    private Date createTime;
    private FriendInvitationStatus friendinvitationStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

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

    public FriendInvitationStatus getFriendinvitationStatus() {
        return friendinvitationStatus;
    }

    public void setFriendinvitationStatus(FriendInvitationStatus friendinvitationStatus) {
        this.friendinvitationStatus = friendinvitationStatus;
    }
}
