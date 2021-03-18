package com.platform.common.web;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-28 10:33
 */
@Data
public class PageParam implements Serializable {
    private static final long serialVersionUID = 7441552405745764825L;
    private long pageNumber;
    private long pageSize;
    private long total;
    private long totalPage;

    public PageParam() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageParam)) {
            return false;
        } else {
            PageParam other = (PageParam)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageNumber() != other.getPageNumber()) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else {
                return this.getTotalPage() == other.getTotalPage();
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageParam;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        long $pageNumber = this.getPageNumber();
        result = result * 59 + (int)($pageNumber >>> 32 ^ $pageNumber);
        long $pageSize = this.getPageSize();
        result = result * 59 + (int)($pageSize >>> 32 ^ $pageSize);
        long $total = this.getTotal();
        result = result * 59 + (int)($total >>> 32 ^ $total);
        long $totalPage = this.getTotalPage();
        result = result * 59 + (int)($totalPage >>> 32 ^ $totalPage);
        return result;
    }

    public String toString() {
        return "ResponseResult.PageParam(pageNumber=" + this.getPageNumber() + ", pageSize=" + this.getPageSize() + ", total=" + this.getTotal() + ", totalPage=" + this.getTotalPage() + ")";
    }
}
