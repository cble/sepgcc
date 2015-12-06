package com.sepgcc.site.dto;

import java.io.Serializable;

public class User implements Serializable {

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
