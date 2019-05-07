package com.ssm.demo.common;

import java.util.List;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/4/29
 */
public class PageBean<T> {
    //当前页
    private Integer currentPage = 1;
    //每页显示的总条数
    private Integer pageSize = 10;
    //总条数
    private Long totalNum;
    //是否有下一页
    private Integer isMore;
    //总页数
    private Integer totalPage;
    //开始索引
    private Integer startIndex;
    //分页结果
    private List<T> items;

    public PageBean() {
        super();
    }

    public PageBean(Integer currentPage, Integer pageSize, Long totalNum){
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        String totalNumStr = String.valueOf(this.totalNum);
        this.totalPage = (Integer.valueOf(totalNumStr) + this.pageSize - 1)/this.pageSize;
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage ? 0 : 1;
    }

    public PageBean(int currentPage, int pageSize, long totalNum, List<T> items) {
        this.totalNum = totalNum;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = pageSize == 0 ? 1 : new Double(Math.ceil(1.0 * totalNum / pageSize)).intValue();
        this.items = items;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getIsMore() {
        return isMore;
    }

    public void setIsMore(Integer isMore) {
        this.isMore = isMore;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
