package com.enguru.wikipedia.service.model.search;

import com.google.gson.annotations.SerializedName;

public class SearchResponseModel {

    @SerializedName("batchcomplete")
    private boolean batchcomplete;

    @SerializedName("continue")
    private JsonMemberContinue jsonMemberContinue;

    @SerializedName("warnings")
    private Warnings warnings;

    @SerializedName("query")
    private Query query;

    public void setBatchcomplete(boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public boolean isBatchcomplete() {
        return batchcomplete;
    }

    public void setJsonMemberContinue(JsonMemberContinue jsonMemberContinue) {
        this.jsonMemberContinue = jsonMemberContinue;
    }

    public JsonMemberContinue getJsonMemberContinue() {
        return jsonMemberContinue;
    }

    public void setWarnings(Warnings warnings) {
        this.warnings = warnings;
    }

    public Warnings getWarnings() {
        return warnings;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return
                "SearchResponseModel{" +
                        "batchcomplete = '" + batchcomplete + '\'' +
                        ",continue = '" + jsonMemberContinue + '\'' +
                        ",warnings = '" + warnings + '\'' +
                        ",query = '" + query + '\'' +
                        "}";
    }
}