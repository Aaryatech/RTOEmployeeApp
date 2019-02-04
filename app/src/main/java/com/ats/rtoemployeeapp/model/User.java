package com.ats.rtoemployeeapp.model;

public class User {

    private int userId;
    private String userName;
    private String userMobile;
    private String userPassword;
    private String userEmail;
    private String userDob;
    private String lastUpdatedTime;
    private int isUsed;
    private String exStr1;
    private String exStr2;
    private int exInt1;
    private int exInt2;
    private String date1;
    private String date2;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getExStr1() {
        return exStr1;
    }

    public void setExStr1(String exStr1) {
        this.exStr1 = exStr1;
    }

    public String getExStr2() {
        return exStr2;
    }

    public void setExStr2(String exStr2) {
        this.exStr2 = exStr2;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userDob='" + userDob + '\'' +
                ", lastUpdatedTime='" + lastUpdatedTime + '\'' +
                ", isUsed=" + isUsed +
                ", exStr1='" + exStr1 + '\'' +
                ", exStr2='" + exStr2 + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                '}';
    }
}
