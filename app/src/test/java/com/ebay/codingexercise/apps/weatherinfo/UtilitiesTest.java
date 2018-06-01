package com.ebay.codingexercise.apps.weatherinfo;

import android.os.Build;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
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
        CityWeather cityWeather = getCityWeather("cityweather");
        queryList.add(new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis()));
        queryList.add(new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis()));
        Assert.assertTrue("@test_convertQueriesToViewModels: Unable to convert query items into view model items",
                AdapterConverter.convertQueriesToViewModels(queryList).size() == 2);
    }

    private CityWeather getCityWeather(String fileName) {
        String result = "";
        String file = String.format("test/%s.json", fileName);
        InputStream bis = null;
        try {
            bis = new BufferedInputStream(
                    RuntimeEnvironment.application.getAssets().open(file));
            byte[] buf = new byte[512];
            int read = 0;
            while ((read = bis.read(buf)) != -1) {
                result += new String(Arrays.copyOfRange(buf, 0, read), "UTF-8");
                ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Type portfolioListType = new TypeToken<CityWeather>() {
        }.getType();
        return new Gson().fromJson(result, portfolioListType);
    }
}
