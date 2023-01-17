package uet.kltn.judgment.dto.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ExpressionDto {
    private boolean isPaging;
    private int limit;
    private int page;
    private int offset;
    private String orderBy;
    private boolean isDesc;
    private Sort sort;

    public ExpressionDto() {
    }

    public ExpressionDto(boolean isPaging, int limit, int page, String orderBy, boolean isDesc) {
        this.isPaging = isPaging;
        this.limit = limit;
        this.page = page;
        this.orderBy = orderBy;
        this.isDesc = isDesc;
        this.offset = (page - 1) * limit;
    }

    public Pageable getPageable() {
        return PageRequest.of(
                this.page - 1,
                this.limit,
                this.getSort()
        );
    }

    public boolean isPaging() {
        return isPaging;
    }

    public void setIsPaging(boolean isPaging) {
        this.isPaging = isPaging;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isDesc() {
        return isDesc;
    }

    public void setDesc(boolean desc) {
        isDesc = desc;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Sort getSort() {
        if (sort == null) {
            return Sort.by(this.isDesc() ? Sort.Direction.DESC : Sort.Direction.ASC, this.orderBy);
        }
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
