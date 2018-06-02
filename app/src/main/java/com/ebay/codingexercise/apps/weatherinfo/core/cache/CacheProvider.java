package com.ebay.codingexercise.apps.weatherinfo.core.cache;

import android.content.Context;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheDeleteListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheWriteListener;

import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public interface CacheProvider {

    void writeObject(Context context, Query object, CacheWriteListener cacheWriteListener);

    void readObjectList(Context context, CacheReadListener cacheReadListener);

    void deleteObjectList(Context context, List<Query> deleteList, CacheDeleteListener cacheDeleteListener);

    void readLastObject(Context context, CacheReadListener cacheReadListener);
}
