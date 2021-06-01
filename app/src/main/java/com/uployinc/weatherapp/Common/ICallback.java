package com.uployinc.weatherapp.Common;

public interface ICallback<T> {
    void onResponse(T response);
    void onError(Exception exception);
}
