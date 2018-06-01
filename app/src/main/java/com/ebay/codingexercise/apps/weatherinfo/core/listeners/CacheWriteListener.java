package com.ebay.codingexercise.apps.weatherinfo.core.listeners;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;

import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public interface CacheWriteListener {

    void onSuccess(Query written);

    void onError();
}
