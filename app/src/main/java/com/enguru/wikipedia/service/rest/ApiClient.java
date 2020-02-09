package com.enguru.wikipedia.service.rest;

import android.annotation.SuppressLint;
import android.content.Context;

import com.enguru.wikipedia.BuildConfig;
import com.enguru.wikipedia.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://en.wikipedia.org//w/";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static Retrofit getClient(Context context) {
        mContext = context;
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        File httpCacheDirectory = new File(context.getCacheDir(), "offlineCache");
        int cacheSize = (5 * 1024 * 1024);
        Cache myCache = new Cache(httpCacheDirectory, cacheSize);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        client.cache(myCache);
        client.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        client.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);

        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private final static Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @NotNull
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private final static Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!CommonUtils.getConnectivityStatus(mContext)) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };
}
