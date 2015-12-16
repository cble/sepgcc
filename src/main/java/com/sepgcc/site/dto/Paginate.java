package com.sepgcc.site.dto;

import java.io.Serializable;
import java.util.List;

public class Paginate<T> implements Serializable {

    private int pageCount;
    private List<T> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
