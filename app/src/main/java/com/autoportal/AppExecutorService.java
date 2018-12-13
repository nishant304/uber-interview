package com.autoportal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutorService {

    public static AppExecutorService getAppExecutorService() {
        return appExecutorService;
    }

    public Executor getDiskIo() {
        return diskIo;
    }

    public Executor getNetworkIo() {
        return networkIo;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private Executor diskIo;

    private Executor networkIo;

    private Executor mainThread;

    private static AppExecutorService appExecutorService = new AppExecutorService();

    private AppExecutorService(){
        diskIo = Executors.newSingleThreadExecutor();
        networkIo = Executors.newFixedThreadPool(3);
        mainThread = new MainThreadExecutor();
    }


    private static class MainThreadExecutor implements Executor {

        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }


}
