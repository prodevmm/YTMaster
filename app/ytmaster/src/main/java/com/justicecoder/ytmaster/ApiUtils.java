package com.justicecoder.ytmaster;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    public static ApiRequest getRequest (String url){
        return new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build().create(ApiRequest.class);
    }
}
