package com.enguru.wikipedia.service.model.error;


import com.google.gson.annotations.SerializedName;

public class ErrorModel {

    @SerializedName("error")
    private Error error;

    @SerializedName("servedby")
    private String servedby;

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setServedby(String servedby) {
        this.servedby = servedby;
    }

    public String getServedby() {
        return servedby;
    }

    @Override
    public String toString() {
        return
                "ErrorModel{" +
                        "error = '" + error + '\'' +
                        ",servedby = '" + servedby + '\'' +
                        "}";
    }
}