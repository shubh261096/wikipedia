package com.enguru.wikipedia.service.model.search;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("pages")
    private List<PagesItem> pages;

    public void setPages(List<PagesItem> pages) {
        this.pages = pages;
    }

    public List<PagesItem> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return
                "Query{" +
                        "pages = '" + pages + '\'' +
                        "}";
    }
}