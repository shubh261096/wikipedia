package com.enguru.wikipedia.service.model.search;

import com.google.gson.annotations.SerializedName;

public class Pageimages {

    @SerializedName("warnings")
    private String warnings;

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getWarnings() {
        return warnings;
    }

    @Override
    public String toString() {
        return
                "Pageimages{" +
                        "warnings = '" + warnings + '\'' +
                        "}";
    }
}