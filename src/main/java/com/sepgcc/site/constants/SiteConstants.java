package com.sepgcc.site.constants;

import com.google.common.collect.Lists;

import java.util.List;

public class SiteConstants {

    public static final int PAGE_SIZE = 10;

    public static final String FILE_BASE = "/data/appdatas/sepgcc-site";
    public static final String[] FILE_EXTENSION_SUPPORT = {
            ".txt", ".pdf",
            ".gif", ".png", ".jpg", ".jpeg", ".bmp",
            ".zip", ".rar", ".7z",
            ".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx"
    };
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 60; // 5MB

    public static final List<Integer> ADMIN_AVAILABLE_PROJECT_STATUS = Lists.newArrayList(0, 1, 2);
    public static final List<Integer> USER_AVAILABLE_PROJECT_STATUS = Lists.newArrayList(1);
    public static final List<Integer> EDITABLE_PROJECT_STATUS = Lists.newArrayList(0);
}
