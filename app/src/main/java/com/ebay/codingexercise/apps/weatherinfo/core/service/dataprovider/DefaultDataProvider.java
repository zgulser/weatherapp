package com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider;

import android.support.annotation.Nullable;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.ServiceConstants;
import com.ebay.codingexercise.apps.weatherinfo.core.service.util.ErrorConverter;

import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class DefaultDataProvider implements DataProvider {

    @Nullable
    private static RetrofitWeatherService retrofitWeatherService;

    public DefaultDataProvider(){
        init();
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitWeatherService = retrofit.create(RetrofitWeatherService.class);
    }

    @Override
    public void getWeatherByCityName(String cityName, final RequestListener requestListener) {
        Call call = retrofitWeatherService.getWeatherByCityName(cityName, ServiceConstants.API_KEY, "metric");
        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    requestListener.onSuccess(response.body());
                } else {
                    requestListener.onError(ErrorConverter.convertError(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                requestListener.onError(ErrorConverter.convertError(-1, t.getMessage()));
            }
        });
    }

    @Override
    public void getWeatherByZipCode(String zipCode, final RequestListener requestListener) {
        Call call = retrofitWeatherService.getWeatherByZipCode(zipCode, ServiceConstants.API_KEY, "metric");
        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    requestListener.onSuccess(response.body());
                } else {
                    requestListener.onError(ErrorConverter.convertError(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                requestListener.onError(ErrorConverter.convertError(-1, t.getMessage()));
            }
        });
    }

    public void getWeatherByGeoLoc(int lat, int lon, final RequestListener requestListener) {
        Call call = retrofitWeatherService.getWeatherByGeoLoc(lat, lon, ServiceConstants.API_KEY, "metric");
        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    requestListener.onSuccess(response.body());
                } else {
                    requestListener.onError(ErrorConverter.convertError(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                requestListener.onError(ErrorConverter.convertError(-1, t.getMessage()));
            }
        });
    }
}
