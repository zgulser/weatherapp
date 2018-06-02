package com.ebay.codingexercise.apps.weatherinfo.core.cache;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheDeleteListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheWriteListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public final class DefaultCacheProvider implements CacheProvider {

    private static final String FILENAME = "queries";
    public static final String BUNDLE_QUERY_LIST_KEY = "queries";

    public DefaultCacheProvider(){
    }

    @Override
    public void writeObject(final Context context, final Query query, @Nullable final CacheWriteListener cacheWriteListener) {
        Thread writer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences pickledQueries = context.getSharedPreferences(FILENAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pickledQueries.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(query);
                    editor.putString(String.valueOf(query.getTimestamp()), json);
                    editor.commit();

                    if (cacheWriteListener != null)
                        cacheWriteListener.onSuccess(query);

                } catch (Exception e){
                    if (cacheWriteListener != null)
                        cacheWriteListener.onError();
                }
            }
        });
        writer.setPriority(Thread.MIN_PRIORITY);
        writer.start();
    }

    /**
     *
     *  Note: Items sent through intents have size limits. Not sure that applies when we use
     *        LocalBroaccastManager since intent does not leave the process sandbox which can
     *        mean no IPC-related constraints. If it's items may be sent using a listener.
     *
     */
    @Override
    public void readObjectList(final Context context, @Nullable final CacheReadListener cacheReadListener) {
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Query> queryList = new ArrayList<>();
                    SharedPreferences pickledQueries = context.getSharedPreferences(FILENAME, MODE_PRIVATE);
                    Collection<String> values = (Collection<String>) pickledQueries.getAll().values();
                    for (String queryJSON : values) {
                        Query query = new Gson().fromJson(queryJSON, Query.class);
                        queryList.add(query);
                    }

                    if (!queryList.isEmpty()) {
                        Collections.sort(queryList, new Comparator<Query>() {
                            @Override
                            public int compare(Query q1, Query q2) {
                                return (int) (q2.getTimestamp() - q1.getTimestamp());
                            }
                        });

                        Intent intent = new Intent(SearchDiskCache.EVENT_QUERY_OBJECT_READ);
                        Bundle queryObject = new Bundle();
                        queryObject.putParcelableArrayList(BUNDLE_QUERY_LIST_KEY, queryList);
                        intent.putExtras(queryObject);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    }

                    if (cacheReadListener != null) {
                        cacheReadListener.onSuccess(queryList);
                    }
                }  catch (Exception e){
                    if (cacheReadListener != null)
                        cacheReadListener.onError();
                }
            }
        });
        reader.setPriority(Thread.MIN_PRIORITY);
        reader.start();
    }

    @Override
    public void deleteObject(Context context, CacheDeleteListener cacheDeleteListener) {
        // no implementation needed
    }
}
