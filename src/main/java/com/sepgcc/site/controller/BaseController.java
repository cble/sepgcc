package com.sepgcc.site.controller;

import org.springframework.web.servlet.mvc.Controller;

public abstract class BaseController implements Controller {

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
