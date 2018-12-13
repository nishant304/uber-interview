package com.autoportal.uber.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Resource<T> {

    public static final int SUCCESS = 0;

    public static final int ERROR = 1;

    public static final int LOADING = 2;

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {SUCCESS, ERROR, LOADING})
    public @interface STATUS {
    }

    private String message;

    private T data;

    private @STATUS int status;

    private Resource(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(data, SUCCESS, null);
    }

    public static <T> Resource<T> error(String message, T data) {
        return new Resource<>(data, ERROR, message);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(null, LOADING, null);
    }

}
