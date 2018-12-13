package com.autoportal.uber.view.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.autoportal.uber.model.Photo;
import com.autoportal.uber.network.Resource;
import com.autoportal.uber.repo.SearchRepo;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final SearchRepo searchRepo;

    public SearchViewModel(SearchRepo searchRepo){
        this.searchRepo = searchRepo;
    }

    public LiveData<Resource<List<Photo>>> searchImage(String s) {
        return searchRepo.searchImage(s);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        private SearchRepo searchRepo;

        public Factory(SearchRepo searchRepo){
            this.searchRepo = searchRepo;
        }


        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchViewModel(searchRepo);
        }
    }


}
