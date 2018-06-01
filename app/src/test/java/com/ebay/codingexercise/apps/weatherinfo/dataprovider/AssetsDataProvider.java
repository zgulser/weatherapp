package com.ebay.codingexercise.apps.weatherinfo.dataprovider;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider.DataProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.robolectric.RuntimeEnvironment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by Backbase R&D B.V on 31/05/2018.
 */
public class AssetsDataProvider implements DataProvider {

    @Override
    public void getWeatherByCityName(String cityName, RequestListener requestListener) {
         CityWeather cityWeather = getCityWeather("weatherdatasample");
         if (cityWeather != null){
             requestListener.onSuccess(cityWeather);
         } else {
             requestListener.onError(new RequestError(String.valueOf(400), ""));
         }
    }

    @Override
    public void getWeatherByZipCode(String zipCode, RequestListener requestListener) {
        CityWeather cityWeather = getCityWeather("weatherdatasample");
        if (cityWeather != null){
            requestListener.onSuccess(cityWeather);
        } else {
            requestListener.onError(new RequestError(String.valueOf(400), ""));
        }
    }

    @Override
    public void getWeatherByGeoLoc(int lat, int lon, RequestListener requestListener) {
        // not implemented
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
