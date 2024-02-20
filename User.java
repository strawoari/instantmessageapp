package com.heiyu.messaging.model;

import com.heiyu.messaging.enums.Gender;

import java.util.Date;

public class User {
    private int id;
    private int kk;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String address;
    private Gender gender;
    private Date registerTime;
    private Boolean isValid;
    private String loginToken;
    private Date lastLoginTime;

    public int getKk() {
        return kk;
    }
    public void setKk(int kk) {
        this.kk = kk;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public Boolean getValid() {
        return isValid;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
