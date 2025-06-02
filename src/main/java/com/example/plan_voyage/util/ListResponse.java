package com.example.plan_voyage.util;

public class ListResponse<T> {
    private T list;
    private long totalItems;
    private int pageSize;
    private int pageNumber;
    private int totalPages;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItem) {
        this.totalItems = totalItem;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
