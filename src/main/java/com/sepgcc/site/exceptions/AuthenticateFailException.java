package com.sepgcc.site.exceptions;

public class AuthenticateFailException extends RuntimeException {

    private int level; // 0-返回404 1-重新登录

    public AuthenticateFailException(int level) {
        super();
        this.level = level;
    }

    public boolean redirectToLogin() {
        return level == 1;
    }
}
