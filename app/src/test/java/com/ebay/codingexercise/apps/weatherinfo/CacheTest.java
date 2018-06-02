package com.ebay.codingexercise.apps.weatherinfo;

import com.ebay.codingexercise.apps.weatherinfo.core.cache.DefaultDiskCacheProvider;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheWriteListener;
import com.ebay.codingexercise.apps.weatherinfo.utils.TestUtility;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Zeki Gulser on 01/06/2018.
 */
@RunWith(RobolectricTestRunnerWithResources.class)
public class CacheTest {

    private CountDownLatch countDownLatch;
    @Before
    public void setup(){
        countDownLatch = new CountDownLatch(1);
        SearchDiskCache.getInstance().setCacheProvider(new DefaultDiskCacheProvider());
    }

    @Test
    public void test_writeObject() throws InterruptedException {
        CityWeather cityWeather = TestUtility.getCityWeather("cityweather");
        final Query query = new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis());
        SearchDiskCache.getInstance().writeObject(RuntimeEnvironment.application.getApplicationContext(), query, new CacheWriteListener() {
            @Override
            public void onSuccess(Query written) {
                Assert.assertTrue("@test_writeObject: Unable to write the query object", query != null);
                countDownLatch.countDown();
            }

            @Override
            public void onError() {
                assert false;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        Thread.sleep(2000); // to prevent a potential race between two read/write threads
    }

    @Test
    public void test_readObject() throws InterruptedException {
        SearchDiskCache.getInstance().readObjectList(RuntimeEnvironment.application.getApplicationContext(), new CacheReadListener() {
            @Override
            public void onSuccess(List<Query> queryList) {
                Assert.assertTrue("@test_readObject: Unable to read the query list", queryList != null);
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
}
