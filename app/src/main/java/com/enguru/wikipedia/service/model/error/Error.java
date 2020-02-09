package com.enguru.wikipedia.service.model.error;

import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("code")
    private String code;

    @SerializedName("docref")
    private String docref;

    @SerializedName("info")
    private String info;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDocref(String docref) {
        this.docref = docref;
    }

    public String getDocref() {
        return docref;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return
                "Error{" +
                        "code = '" + code + '\'' +
                        ",docref = '" + docref + '\'' +
                        ",info = '" + info + '\'' +
                        "}";
    }
}