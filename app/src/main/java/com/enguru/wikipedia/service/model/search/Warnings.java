package com.enguru.wikipedia.service.model.search;

import com.google.gson.annotations.SerializedName;

public class Warnings {

    @SerializedName("pageimages")
    private Pageimages pageimages;

    public void setPageimages(Pageimages pageimages) {
        this.pageimages = pageimages;
    }

    public Pageimages getPageimages() {
        return pageimages;
    }

    @Override
    public String toString() {
        return
                "Warnings{" +
                        "pageimages = '" + pageimages + '\'' +
                        "}";
    }
}