package com.ebay.codingexercise.apps.weatherinfo.core.application;

import android.app.Application;

import com.ebay.codingexercise.apps.weatherinfo.core.cache.DefaultDiskCacheProvider;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;

/**
 * Created by Zeki Gulser on 31/05/2018.
 * App entry point: Used as a composition root
 */
public class WeatherApplication extends Application {

    private static final String TAG = WeatherApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        SearchDiskCache searchDiskCache = SearchDiskCache.getInstance();
        searchDiskCache.setCacheProvider(new DefaultDiskCacheProvider());
    }
}
