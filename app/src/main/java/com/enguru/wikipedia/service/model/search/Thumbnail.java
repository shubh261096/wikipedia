package com.enguru.wikipedia.service.model.search;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {

    @SerializedName("width")
    private int width;

    @SerializedName("source")
    private String source;

    @SerializedName("height")
    private int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return
                "Thumbnail{" +
                        "width = '" + width + '\'' +
                        ",source = '" + source + '\'' +
                        ",height = '" + height + '\'' +
                        "}";
    }
}