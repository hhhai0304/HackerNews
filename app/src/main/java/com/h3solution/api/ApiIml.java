package com.h3solution.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.h3solution.listener.OnDataReceived;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ApiIml
 * Created by HHHai on 29-05-2017.
 */
public class ApiIml {
    private final String TAG = getClass().getSimpleName();

    private Api api;
    private Call<ResponseBody> call;
    private OnDataReceived onReceived;

    public ApiIml() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hacker-news.firebaseio.com/v0/")
                .build();

        this.api = retrofit.create(Api.class);
    }

    public void getDataFromApi(String url, OnDataReceived onDataReceived) {
        this.onReceived = onDataReceived;
        call = api.sendToApi(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        onReceived.onReceived(result);
                        Log.d(TAG, result);
                    } catch (Exception e) {
                        Log.e(TAG + " onResponse", e.getMessage());
                        onReceived.onFail(e);
                    }
                } else {
                    Log.e(TAG + " isSuccessful", "Fail");
                    onReceived.onFail(new Exception("Fail"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG + " onFailure", t.getMessage());
                onReceived.onFail(new Exception(t));
            }
        });
    }

    public void cancel() {
        call.cancel();
    }
}