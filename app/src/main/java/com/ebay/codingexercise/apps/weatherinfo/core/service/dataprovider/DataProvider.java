package com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public interface DataProvider {

    void getWeatherByCityName(String cityName, RequestListener requestListener);

    void getWeatherByZipCode(String zipCode, RequestListener requestListener);

    void getWeatherByGeoLoc(int lat, int lon, RequestListener requestListener);
}
