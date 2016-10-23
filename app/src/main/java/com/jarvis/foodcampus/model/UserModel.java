package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-10-22.
 */

// UserModel 정의 .
public class UserModel {
    private long id;
    private String nickName;

    public UserModel(long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }
}
