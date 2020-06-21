package com.leyou.common;

import java.util.List;

public class PageResult<T> {

    private Long total; //总条数

    private List<T> items; //数据

    private Integer totalpage; //总页数

    public PageResult(Long total, List<T> items, Integer totalpage) {
        this.total = total;
        this.items = items;
        this.totalpage = totalpage;
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public PageResult(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
