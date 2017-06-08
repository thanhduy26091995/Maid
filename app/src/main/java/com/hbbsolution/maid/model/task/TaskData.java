
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskData {

    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pages")
    @Expose
    private Integer pages;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TaskData() {
    }

    /**
     * 
     * @param limit
     * @param total
     * @param page
     * @param docs
     * @param pages
     */
    public TaskData(List<Doc> docs, Integer total, Integer limit, Integer page, Integer pages) {
        super();
        this.docs = docs;
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.pages = pages;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

}
