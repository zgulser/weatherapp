package com.ebay.codingexercise.apps.weatherinfo.utils;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.robolectric.RuntimeEnvironment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by Zeki Gulser on 02/06/2018.
 */
public class TestUtility {

    public static CityWeather getCityWeather(String fileName) {
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
