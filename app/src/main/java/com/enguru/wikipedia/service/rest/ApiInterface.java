package com.enguru.wikipedia.service.rest;

import com.enguru.wikipedia.service.model.search.SearchResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("api.php")
    Call<SearchResponseModel> getSearchResult(@Query("action") String action,
                                              @Query("format") String format,
                                              @Query("prop") String prop,
                                              @Query("redirects") Integer redirects,
                                              @Query("generator") String generator,
                                              @Query("formatversion") Integer formatversion,
                                              @Query("piprop") String piprop,
                                              @Query("pithumbsize") Integer pithumbsize,
                                              @Query("pilimit") Integer pilimit,
                                              @Query("gpssearch") String gpssearch,
                                              @Query("gpslimit") Integer gpslimit,
                                              @Query("wbptterms") String wbptterms);

}