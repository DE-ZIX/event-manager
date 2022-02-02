package com.eventmanager.demo.pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;

public class Pagination<E> {
    public Pagination() {

    }

    public Class<E> type;
    public Integer page;
    public Boolean descending;
    public Integer limit;
    public String sortBy;

    public void init(Class <E> clazz) {
        this.type = clazz;
        this.page = this.page == null || this.page < 0 ? 0 : this.page;
        if(this.page > 0) this.page--;
        this.descending = this.descending != null && this.descending;
        this.limit = this.limit == null || this.limit > 1000 || this.limit < 1 ? 1000 : this.limit;
    }

    public PageRequest toPageRequest() {
        if (this.sortBy != null && !this.sortBy.isEmpty()) {
            boolean containsField = this.doesContainField(this.sortBy);
            String field = containsField ? this.sortBy : "id";
            return PageRequest.of(this.page, this.limit, Sort.by(this.descending ? Sort.Direction.DESC : Sort.Direction.ASC, field));
        }
        return PageRequest.of(this.page, this.limit);
    }
    public boolean doesContainField(String fieldName) {
        for (Field field : type.getFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
