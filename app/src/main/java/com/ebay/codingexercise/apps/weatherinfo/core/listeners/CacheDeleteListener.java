package com.ebay.codingexercise.apps.weatherinfo.core.listeners;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public interface CacheDeleteListener {

    void onSuccess(String key);

    void onError();
}
