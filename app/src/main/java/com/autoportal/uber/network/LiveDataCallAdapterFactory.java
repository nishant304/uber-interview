package com.autoportal.uber.network;

import android.arch.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type observableType = getParameterUpperBound(0, (ParameterizedType) type);
        return new LiveDataCallAdapter<>(getParameterUpperBound(0, (ParameterizedType) observableType));
    }

    public static class LiveDataCallAdapter<R> implements CallAdapter<R,LiveData<ApiResponse<R>>>{

        private Type returnType;

        public LiveDataCallAdapter(Type returnType) {
            this.returnType = returnType;
        }

        @Override
        public Type responseType() {
            return returnType;
        }

        @Override
        public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
            return new LiveData<ApiResponse<R>>() {

                private AtomicBoolean isStarted = new AtomicBoolean(false);

                @Override
                protected void onActive() {
                    if(isStarted.compareAndSet(false,true)){
                        call.enqueue(new Callback<R>() {
                            @Override
                            public void onResponse(Call<R> call, Response<R> response) {
                                postValue(new ApiResponse<>(response));
                            }

                            @Override
                            public void onFailure(Call<R> call, Throwable throwable) {
                                postValue(new ApiResponse<R>(throwable));
                            }
                        });
                    }
                }
            };
        }
    }


}
