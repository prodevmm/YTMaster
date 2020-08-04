package com.justicecoder.ytmaster;

import com.justicecoder.ytmaster.models.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {

    @FormUrlEncoded
    @POST("{path}")
    Call<Response> getResponse (@Path ("path") String path, @Field("videoId") String videoId);
}
