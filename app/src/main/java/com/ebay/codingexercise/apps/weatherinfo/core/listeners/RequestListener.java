package com.ebay.codingexercise.apps.weatherinfo.core.listeners;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;

import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public interface RequestListener {

    void onSuccess(CityWeather cityWeather);

    void onError(RequestError requestError);
}
