package com.autoportal.uber.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.autoportal.uber.model.FlickrResponse;
import com.autoportal.uber.model.Photo;
import com.autoportal.uber.network.ApiResponse;
import com.autoportal.uber.network.RequestManager;
import com.autoportal.uber.network.Resource;

import java.util.List;

public class SearchRepo {

    public LiveData<Resource<List<Photo>>> searchImage(String s) {
        final MediatorLiveData<Resource<List<Photo>>> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.setValue(Resource.<List<Photo>>loading());

        final LiveData<ApiResponse<FlickrResponse>> liveData = RequestManager.getRequestManager().searchImage(s);
        mediatorLiveData.addSource(liveData, new Observer<ApiResponse<FlickrResponse>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<FlickrResponse> flickrResponseApiResponse) {
                mediatorLiveData.removeSource(liveData);
                if(flickrResponseApiResponse.isSuccess() && flickrResponseApiResponse.getResponse() != null){
                     mediatorLiveData.setValue(Resource.success(getFormattedResponse(flickrResponseApiResponse.getResponse().getPhotos().getPhoto())));
                }else {
                    mediatorLiveData.setValue(Resource.<List<Photo>>error(flickrResponseApiResponse.getErrorMessage(),null));
                }
            }
        });
        return mediatorLiveData;
    }

    private List<Photo> getFormattedResponse(List<Photo> photos) {
        for(Photo photo : photos){
            StringBuilder stringBuilder = new StringBuilder("http://farm");
            photo.setUrl(stringBuilder.append(photo.getFarm()).append(".static.flickr.com/").append(photo.getServer()).append("/").append(photo.getId())
            .append("_").append(photo.getSecret()).append(".jpg").toString());
        }
        return photos;
    }

}
