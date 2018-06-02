package com.ebay.codingexercise.apps.weatherinfo;

import android.os.Build;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.service.util.ErrorConverter;
import com.ebay.codingexercise.apps.weatherinfo.utils.TestUtility;
import com.ebay.codingexercise.apps.weatherinfo.view.util.AdapterConverter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zeki Gulser on 01/06/2018.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunnerWithResources.class)
public class UtilitiesTest {

    @Test
    public void test_convertQueriesToViewModels(){
        List<Query> queryList = new ArrayList<>();
        CityWeather cityWeather = TestUtility.getCityWeather("cityweather");
        queryList.add(new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis()));
        queryList.add(new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis()));
        Assert.assertTrue("@test_convertQueriesToViewModels: Unable to convert query items into view model items",
                AdapterConverter.convertQueriesToViewModels(queryList).size() == 2);
    }

    @Test
    public void test_convertError(){
        RequestError requestError = ErrorConverter.convertError(400, "Bad Request");
        Assert.assertTrue("@test_convertError: Unable to convert information into a request error obj",
                Integer.valueOf(requestError.getCode()) == 400);
        Assert.assertTrue("@test_convertError: Unable to convert information into a request error obj",
                requestError.getMessage().equals("Bad Request"));
    }
}
