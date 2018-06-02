package com.ebay.codingexercise.apps.weatherinfo.core.cache;

import android.content.Context;
import android.util.Log;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheDeleteListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheWriteListener;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public final class SearchDiskCache {

    private static final String TAG = SearchDiskCache.class.getSimpleName();
    private static SearchDiskCache searchDiskCache;
    private CacheProvider cacheProvider;
    public static final String EVENT_QUERY_OBJECT_READ = "com.ebay.codingexercise.apps.weatherinfo_EVENT_QUERY_OBJECT_READ";
    public static final String EVENT_QUERY_OBJECT_WRITE = "com.ebay.codingexercise.apps.weatherinfo_EVENT_QUERY_OBJECT_WRITE";

    private  SearchDiskCache(){ }

    public static SearchDiskCache getInstance() {
        if (searchDiskCache == null){
            searchDiskCache = new SearchDiskCache();
        }
        return searchDiskCache;
    }

    public void setCacheProvider(CacheProvider cacheProvider){
        this.cacheProvider = cacheProvider;
    }

    public synchronized void writeObject(Context context, Query object, CacheWriteListener cacheWriteListener){
        if (cacheProvider != null){
            cacheProvider.writeObject(context, object, cacheWriteListener);
        } else {
            Log.d(TAG, "SearchDiskCache.writeObject: No cache provider has been set.");
        }
    }

    public synchronized void readObjectList(Context context, CacheReadListener cacheReadListener){
        if (cacheProvider != null){
            cacheProvider.readObjectList(context, cacheReadListener);
        } else {
            Log.d(TAG, "SearchDiskCache.writeObject: No cache provider has been set.");
        }
    }

    public synchronized void deleteObject(Context context, CacheDeleteListener cacheDeleteListener){
        if (cacheProvider != null){
            cacheProvider.deleteObject(context, cacheDeleteListener);
        } else {
            Log.d(TAG, "SearchDiskCache.deleteObject: No cache provider has been set.");
        }
    }
}
