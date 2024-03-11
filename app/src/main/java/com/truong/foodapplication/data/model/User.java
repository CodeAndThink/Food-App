package com.truong.foodapplication.data.model;

public class User {
    public User(String userName, String userPassword, String displayName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.displayName = displayName;
    }
    public User(String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
    }
    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    private String userName;
    private String userPassword;
    private String displayName;

}
