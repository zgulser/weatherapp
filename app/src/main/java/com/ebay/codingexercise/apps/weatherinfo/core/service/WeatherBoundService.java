package com.ebay.codingexercise.apps.weatherinfo.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider.DataProvider;

/**
 * Created by Zeki Gulser R&D B.V on 31/05/2018.
 */
public class WeatherBoundService extends Service {

    private DataProvider dataProvider;
    private final IBinder weatherServiceBinder = new WeatherServiceBinder();

    @VisibleForTesting
    public WeatherBoundService(){ }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return weatherServiceBinder;
    }

    public void setDataProvider(DataProvider dataProvider){
        this.dataProvider = dataProvider;
    }

    public void getWeatherByCityName(final String cityName, final RequestListener requestListener){
        if (dataProvider != null ) {
            dataProvider.getWeatherByCityName(cityName, new RequestListener() {
                @Override
                public void onSuccess(CityWeather cityWeather) {
                    SearchDiskCache.getInstance().writeObject(getApplicationContext(),
                            new Query(cityWeather.getName(), cityWeather, System.currentTimeMillis()),
                            null);
                    requestListener.onSuccess(cityWeather);
                }

                @Override
                public void onError(RequestError requestError) {
                    requestListener.onError(requestError);
                }
            });
        }
        else
            throw new RuntimeException("@WeatherBoundService.getWeatherByCityName: No data provider is set to the service");
    }

    public void getWeatherByZipCode(final String zipCode, final RequestListener requestListener ){
        if (dataProvider != null) {
            dataProvider.getWeatherByZipCode(zipCode, new RequestListener() {
                @Override
                public void onSuccess(CityWeather cityWeather) {
                    SearchDiskCache.getInstance().writeObject(getApplicationContext(),
                            new Query(zipCode, cityWeather, System.currentTimeMillis()),
                            null);
                    requestListener.onSuccess(cityWeather);
                }

                @Override
                public void onError(RequestError requestError) {
                    requestListener.onError(requestError);
                }
            });
        } else
            throw new RuntimeException("@WeatherBoundService.getWeatherByZipCode: No data provider is set to the service");
    }

    public void getWeatherByGeoLoc(final int lat, final int lon, final RequestListener requestListener){
        if (dataProvider != null) {
            dataProvider.getWeatherByGeoLoc(lat, lon, new RequestListener() {
                @Override
                public void onSuccess(CityWeather cityWeather) {
                    SearchDiskCache.getInstance().writeObject(getApplicationContext(),
                            new Query(String.valueOf(cityWeather.getCoordinate().getLatitude()) + "," + String.valueOf(cityWeather.getCoordinate().getLongitude()),
                                    cityWeather,
                                    System.currentTimeMillis()),
                            null);
                    requestListener.onSuccess(cityWeather);
                }

                @Override
                public void onError(RequestError requestError) {
                    requestListener.onError(requestError);
                }
            });
        } else
            throw new RuntimeException("@WeatherBoundService.getWeatherByGeoLoc: No data provider is set to the service");
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class WeatherServiceBinder extends Binder {
        public WeatherBoundService getService() {
            return WeatherBoundService.this;
        }
    }

}
