package com.autoportal.uber.network;

import android.arch.lifecycle.LiveData;

import com.autoportal.uber.model.FlickrResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppService {

    @GET("services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=2")
    LiveData<ApiResponse<FlickrResponse>> searchImage(@Query("text") String searchText);


}
