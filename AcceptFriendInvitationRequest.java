package com.heiyu.messaging.request;

public class AcceptFriendInvitationRequest {
    private int friendInvitationId;
    private String loginToken;

    public int getFriendInvitationId() {
        return friendInvitationId;
    }

    public void setFriendInvitationId(int friendInvitationId) {
        this.friendInvitationId = friendInvitationId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
