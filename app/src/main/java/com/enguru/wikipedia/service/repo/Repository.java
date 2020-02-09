package com.enguru.wikipedia.service.repo;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.enguru.wikipedia.service.model.error.Error;
import com.enguru.wikipedia.service.model.error.ErrorModel;
import com.enguru.wikipedia.service.model.search.SearchResponseModel;
import com.enguru.wikipedia.service.rest.ApiClient;
import com.enguru.wikipedia.service.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.enguru.wikipedia.utils.AppConstants.ErrorConstants.BAD_REQUEST_ERROR;
import static com.enguru.wikipedia.utils.AppConstants.ErrorConstants.SERVER_ERROR;
import static com.enguru.wikipedia.utils.AppConstants.ErrorConstants.SOCKET_ERROR;
import static com.enguru.wikipedia.utils.AppConstants.ErrorConstants.UNKNOWN_ERROR;


public class Repository {
    private static ApiInterface apiService;

    public Repository(Context context) {
        apiService = ApiClient.getClient(context).create(ApiInterface.class);
    }

    /**
     * Wikipedia Search Request
     */
    public void getSearchResult(String searchText, final MutableLiveData<Events.SearchResponseEvent> searchResponseEventMutableLiveData) {
        apiService.getSearchResult("query", "json", "pageimages|pageterms", 1, "prefixsearch", 2, "thumbnail", 50, 100, searchText, 10, "description")
                .enqueue(new Callback<SearchResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SearchResponseModel> call, @Nullable Response<SearchResponseModel> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                searchResponseEventMutableLiveData.
                                        postValue(new Events.SearchResponseEvent(null, true, response.body()));
                            } else {
                                searchResponseEventMutableLiveData.
                                        postValue(new Events.SearchResponseEvent(buildErrorModel(response.code(), response.errorBody()), false, null));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SearchResponseModel> call, @NonNull Throwable t) {
                        String errorMsg = handleFailureResponse(t);
                        ErrorModel errorModel = new ErrorModel();
                        Error error = new Error();
                        error.setInfo(errorMsg);
                        errorModel.setError(error);
                        searchResponseEventMutableLiveData.
                                postValue(new Events.SearchResponseEvent(errorModel, false, null));
                    }
                });
    }


    /* Handle Retrofit Response Failure */
    private String handleFailureResponse(Throwable throwable) {
        if (throwable instanceof IOException) {
            return SOCKET_ERROR;
        } else {
            return UNKNOWN_ERROR;
        }
    }

    private ErrorModel buildErrorModel(int responseCode, ResponseBody body) {
        Gson gson = new GsonBuilder().create();
        ErrorModel mError = new ErrorModel();
        Error error = new Error();
        try {
            mError = gson.fromJson(body.string(), ErrorModel.class);
        } catch (Exception e) {
            if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST && responseCode < HttpURLConnection.HTTP_INTERNAL_ERROR) {
                error.setInfo(BAD_REQUEST_ERROR);
                mError.setError(error);
            } else if (responseCode >= HttpURLConnection.HTTP_INTERNAL_ERROR && responseCode < (HttpURLConnection.HTTP_INTERNAL_ERROR + 100)) {
                error.setInfo(SERVER_ERROR);
                mError.setError(error);
            } else {
                error.setInfo(UNKNOWN_ERROR);
                mError.setError(error);
            }
        }
        return mError;
    }

}
