package com.autoportal.uber.network;


import android.arch.lifecycle.LiveData;

import com.autoportal.AppExecutorService;
import com.autoportal.uber.model.FlickrResponse;

import java.util.concurrent.ExecutorService;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    private static RequestManager requestManager = new RequestManager();

    private final AppService appService;


    private RequestManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        appService = retrofit.create(AppService.class);
    }

    public static RequestManager getRequestManager() {
        return requestManager;
    }


    public LiveData<ApiResponse<FlickrResponse>> searchImage(String searchText){
        return appService.searchImage(searchText);
    }



}
