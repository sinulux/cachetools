package com.sinosoft.vms.rtmptools.entity;

import java.io.Serializable;

public class AuthUser implements Serializable {

    private String userName ;

    private String userPassword;

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

    @Override
    public String toString() {
        return "{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
