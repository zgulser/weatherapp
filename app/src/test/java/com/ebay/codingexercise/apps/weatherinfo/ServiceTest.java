package com.ebay.codingexercise.apps.weatherinfo;

import android.os.Build;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.WeatherBoundService;
import com.ebay.codingexercise.apps.weatherinfo.dataprovider.AssetsDataProvider;
import com.ebay.codingexercise.apps.weatherinfo.dataprovider.NetworkDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zeki Gulser on 31/05/2018.
 *
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunnerWithResources.class)
public class ServiceTest {

    private final CountDownLatch latch = new CountDownLatch(1);
    private WeatherBoundService weatherBoundService;

    @Before
    public void setup() {
        weatherBoundService = Robolectric.buildService(WeatherBoundService.class).bind().get();
    }

    @Test
    public void test_getWeatherByCityName() throws InterruptedException {
        weatherBoundService.setDataProvider(new AssetsDataProvider());
        final String cityName = "Rome";
        weatherBoundService.getWeatherByCityName(cityName, new RequestListener() {
            @Override
            public void onSuccess(CityWeather cityWeather) {
                assertTrue("@test_getWeatherByCityName: unable to fetch city weather", cityWeather.getName().equals(cityName));
                latch.countDown();
            }

            @Override
            public void onError(RequestError requestError) {
                assert false;
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void test_getWeatherByCityZipCode() throws InterruptedException {
        weatherBoundService.setDataProvider(new NetworkDataProvider());
        final String cityName = "94040,us";
        weatherBoundService.getWeatherByZipCode(cityName, new RequestListener() {
            @Override
            public void onSuccess(CityWeather cityWeather) {
                assertTrue("@test_getWeatherByCityZipCode: unable to fetch city weather by zipcode", cityWeather.getName().equals("Mountain View"));
                latch.countDown();
            }

            @Override
            public void onError(RequestError requestError) {
                assert false;
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void test_getWeatherByGeoLoc() throws InterruptedException {
        weatherBoundService.setDataProvider(new NetworkDataProvider());
        int lat = 35;
        int lon = 139;
        weatherBoundService.getWeatherByGeoLoc(lat, lon, new RequestListener() {
            @Override
            public void onSuccess(CityWeather cityWeather) {
                assertTrue("@test_getWeatherByGeoLoc: unable to fetch city weather by (lat, lon)", cityWeather.getName().equals("Shuzenji"));
                latch.countDown();
            }

            @Override
            public void onError(RequestError requestError) {
                assert false;
                latch.countDown();
            }
        });
        latch.await();
    }
}