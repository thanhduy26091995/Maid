package com.hbbsolution.maid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buivu on 04/05/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "https://yukotest123.herokuapp.com/";
    public static String language = "en";
    public static String token = "5ddb93c96664e7b8e18f68850178dea6e3189e52c6532db4d02d21d1c82333dbe485e957e34fa9c30529ab9cdd5d9069c4666972ab6499f0a6b73030a2176a4c";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL + language + "/")
                    .client(okHttpClient(token))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient okHttpClient(final String token) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request().newBuilder()
                                .addHeader("hbbgvauth", token).build();
                        return chain.proceed(request);

                    }
                })
                .build();

        return okHttpClient;
    }

    public static void setLanguage(String lang) {
        language = lang;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + language + "/")
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void setToken(String tokent) {
        token = tokent;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + language + "/")
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
