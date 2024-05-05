package ir.inabama.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ListRequest {
    private final static Logger logger = LoggerFactory.getLogger(ListRequest.class);

    private int pageNumber;
    private int pageSize;

    private String sortField;
    private String order;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Pageable createPageabel() {
        if (this.getPageNumber() < 0) {
            logger.error("Invalid offset: {}", this.getPageNumber());
            return null;
        }

        if (this.getPageSize() < 1) {
            logger.error("Invalid size: {}", this.getPageSize());
            return null;
        }

        Sort.Direction direction;
        String order = this.getOrder();
        if ((order != null) && (order.equals("ASC"))) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        if (this.getSortField() == null) {
            this.setSortField("updated_at");
        }

        return PageRequest.of(this.getPageNumber() - 1,
                this.getPageSize(), direction, this.getSortField());
    }
}
