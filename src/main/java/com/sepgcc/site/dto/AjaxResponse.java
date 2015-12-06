package com.sepgcc.site.dto;

import java.util.Map;

public class AjaxResponse {
    private int code;
    private Map<String, Object> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
