package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-10-22.
 */

// UserModel 정의 .
public class UserModel {
    private long api_id;
    private String nickName;
    private int user_id;

    public UserModel(long id, String nickName) {
        this.api_id = id;
        this.nickName = nickName;
    }

    public long getApi_id() {
        return api_id;
    }

    public void setApi_id(long api_id) {
        this.api_id = api_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
