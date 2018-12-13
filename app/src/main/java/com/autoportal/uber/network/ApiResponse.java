package com.autoportal.uber.network;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class ApiResponse<T> {

    private T data;

    private int code;

    private String errorMessage;

    public ApiResponse(Response<T> response) {
        this.code = response.code();
        if (response.isSuccessful()) {
            this.data = response.body();
        } else {
            String message = null;

            try {
                if (response.errorBody() != null) {
                    message = response.errorBody().string();
                }
            } catch (NullPointerException | IOException ignored ) {

            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = "Something went wrong";
        }

    }

    public ApiResponse(Throwable throwable) {
        code = 500;

        if (throwable instanceof HttpException) {
            errorMessage = "Something went wrong";
        } else if (throwable instanceof IOException) {
            this.errorMessage = "No Internet";
        } else {
            errorMessage = "Something went wrong";
        }
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }

    public T response() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResponse() {
        return data;
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
