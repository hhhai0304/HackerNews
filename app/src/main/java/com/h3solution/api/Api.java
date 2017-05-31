package com.h3solution.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Api
 * Created by HHHai on 29-05-2017.
 */
public interface Api {
    @GET
    Call<ResponseBody> sendToApi(@Url String url);
}