package com.ebay.codingexercise.apps.weatherinfo.core.service;

import com.ebay.codingexercise.apps.weatherinfo.BuildConfig;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class ServiceConstants {

    private ServiceConstants() {}

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String WEATHER_PATH = "weather";
    public static final String API_KEY = BuildConfig.OPENWEATHER_API_KEY;
    public static final String QUERY_API_KEY = "APPID";
    public static final String QUERY_BY_CITY_NAME = "q";
    public static final String QUERY_BY_ZIP_CODE = "zip";
    public static final String QUERY_BY_COOR_LAT = "lat";
    public static final String QUERY_BY_COOR_LON = "lon";
    public static final String QUERY_UNIT = "units";
}
