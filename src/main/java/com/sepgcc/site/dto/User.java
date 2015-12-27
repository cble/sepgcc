package com.sepgcc.site.dto;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String nickname;
    private int userGroup; // 0-普通用户 99-管理员
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(int userGroup) {
        this.userGroup = userGroup;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return userGroup == 99;
    }

    public boolean isEnable() {
        return userGroup > 0;
    }

    public String getGroupStr() {
        switch (userGroup) {
            case 1: return "学校用户";
            case 2: return "会展用户";
            case 99: return "管理员";
            default: return "停用用户";
        }
    }
}
