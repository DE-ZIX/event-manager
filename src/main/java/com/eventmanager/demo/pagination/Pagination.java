package com.eventmanager.demo.pagination;

public class Pagination {
    public Pagination() {

    }

    public Integer page;
    public Boolean descending;
    public Integer limit;
    public String sortBy;

    public void init() {
        this.page = this.page == null || this.page < 0 ? 0 : this.page;
        this.descending = this.descending != null && this.descending;
        this.limit = this.limit == null || this.limit > 1000 || this.limit < 1 ? 1000 : this.limit;
    }
}
