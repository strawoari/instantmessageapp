package com.heiyu.messaging.model;

public class UserValidationCode {
    private String validationCode;
    private int userId;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
