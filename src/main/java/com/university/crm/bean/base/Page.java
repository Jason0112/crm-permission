package com.university.crm.bean.base;

import java.io.Serializable;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class Page implements Serializable {

    private int pageNumber = 1;//当前页
    private int pageSize = 10;//每页条数
    private int pageTotal = 1;//总条数
    private int pageCount = 1;//总页数
    private int startRow = 0;//起始页
    private String sort;//排序字段
    private String order;//排序方式

    public int getPageNumber() {
        if (startRow > 0) {
            return startRow / pageSize + 1;
        }
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

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
}
