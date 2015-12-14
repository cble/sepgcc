package com.sepgcc.site.utils;

import org.springframework.util.DigestUtils;

public class Md5Utils {
    public static String md5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes).toUpperCase();
    }
}
