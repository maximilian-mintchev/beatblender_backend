package com.app.server.model.pagination;

public class PageRequest {

    String sortBy;
    Integer pageNo;
    Integer pageSize;

    public PageRequest(String sortBy, Integer pageNo, Integer pageSize) {
        this.sortBy = sortBy;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
