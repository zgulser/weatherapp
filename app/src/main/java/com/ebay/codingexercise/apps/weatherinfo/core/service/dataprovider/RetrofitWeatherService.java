package com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.service.ServiceConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public interface RetrofitWeatherService {

    @GET(ServiceConstants.WEATHER_PATH)
    Call<CityWeather> getWeatherByCityName(
            @Query(ServiceConstants.QUERY_BY_CITY_NAME) String cityName,
            @Query(ServiceConstants.QUERY_API_KEY) String key,
            @Query(ServiceConstants.QUERY_UNIT) String unit);

    @GET(ServiceConstants.WEATHER_PATH)
    Call<CityWeather> getWeatherByZipCode(
            @Query(ServiceConstants.QUERY_BY_ZIP_CODE) String zipCode,
            @Query(ServiceConstants.QUERY_API_KEY) String key,
            @Query(ServiceConstants.QUERY_UNIT) String unit);

    @GET(ServiceConstants.WEATHER_PATH)
    Call<CityWeather> getWeatherByGeoLoc(
            @Query(ServiceConstants.QUERY_BY_COOR_LAT) int lat,
            @Query(ServiceConstants.QUERY_BY_COOR_LON) int lon,
            @Query(ServiceConstants.QUERY_API_KEY) String key,
            @Query(ServiceConstants.QUERY_UNIT) String unit);

}
