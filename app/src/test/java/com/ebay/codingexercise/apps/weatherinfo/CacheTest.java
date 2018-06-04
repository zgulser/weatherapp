package com.ebay.codingexercise.apps.weatherinfo;

import android.content.Context;
import android.content.SharedPreferences;

import com.ebay.codingexercise.apps.weatherinfo.core.cache.DefaultDiskCacheProvider;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheWriteListener;
import com.ebay.codingexercise.apps.weatherinfo.utils.TestUtility;
import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zeki Gulser on 01/06/2018.
 */
@RunWith(RobolectricTestRunnerWithResources.class)
public class CacheTest {

    private CountDownLatch countDownLatch;
    private Context context;

    @Before
    public void setup() {
        this.context = RuntimeEnvironment.application.getApplicationContext();
        this.countDownLatch = new CountDownLatch(1);
        SearchDiskCache.getInstance().setCacheProvider(new DefaultDiskCacheProvider());
    }

    @Test
    public void test_writeObject() throws InterruptedException {
        CityWeather cityWeather = TestUtility.getCityWeather("cityweather");
        final Query query = new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis());
        SearchDiskCache.getInstance().writeObject(RuntimeEnvironment.application.getApplicationContext(), query, new CacheWriteListener() {
            @Override
            public void onSuccess(Query written) {
                Assert.assertTrue("@test_writeObject: Unable to write the query object", query.getCityWeather().getName().equals("Rome"));
                countDownLatch.countDown();
            }

            @Override
            public void onError() {
                assert false;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    @Test
    public void test_readObjectList() throws InterruptedException {
        writeDummyObject(123456);
        SearchDiskCache.getInstance().readObjectList(RuntimeEnvironment.application.getApplicationContext(), new CacheReadListener() {
            @Override
            public void onSuccess(List<Query> queryList) {
                Assert.assertTrue("@test_readObject: Unable to read the query list", queryList.size() > 0);
                countDownLatch.countDown();
            }

            @Override
            public void onError() {
                assert false;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    @Test
    public void test_readLastObject() throws InterruptedException {
        writeDummyObject(123456);
        writeDummyObject(123457);
        SearchDiskCache.getInstance().readLastObject(RuntimeEnvironment.application.getApplicationContext(), new CacheReadListener() {
            @Override
            public void onSuccess(List<Query> queryList) {
                Query last = queryList.get(0);
                Assert.assertTrue("@test_readLastObject: Unable to read the query list", last.getTimestamp() == 123457);
                countDownLatch.countDown();
            }

            @Override
            public void onError() {
                assert false;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    private boolean writeDummyObject(final long key){
        CityWeather cityWeather = TestUtility.getCityWeather("cityweather");
        final Query query = new Query(cityWeather.getName(), cityWeather, key);
        SharedPreferences pickledQueries = context.getSharedPreferences(DefaultDiskCacheProvider.FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pickledQueries.edit();
        Gson gson = new Gson();
        String json = gson.toJson(query);
        editor.putString(String.valueOf(query.getTimestamp()), json);
        editor.putString(DefaultDiskCacheProvider.LAST_ITEM, String.valueOf(query.getTimestamp()));
        return editor.commit();
    }
}
