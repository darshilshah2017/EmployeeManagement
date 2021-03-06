package com.infy.dto;

public class PageDataDTO {

    private int page;
    private int size;
    private String sort;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PageDataDTO{" +
                "page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                '}';
    }
}
