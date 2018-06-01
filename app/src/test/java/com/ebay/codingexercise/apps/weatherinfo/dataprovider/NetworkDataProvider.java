package com.ebay.codingexercise.apps.weatherinfo.dataprovider;

import android.support.annotation.Nullable;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.ServiceConstants;
import com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider.DataProvider;
import com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider.RetrofitWeatherService;
import com.ebay.codingexercise.apps.weatherinfo.core.service.util.ErrorConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Backbase R&D B.V on 31/05/2018.
 */
public class NetworkDataProvider implements DataProvider {

    private static String GET_URL = ServiceConstants.BASE_URL;

    @Override
    public void getWeatherByCityName(String cityName, final RequestListener requestListener){
        // not implemented
    }

    @Override
    public void getWeatherByZipCode(final String zipCode, final RequestListener requestListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    URL obj = new URL(GET_URL + String.format("weather?zip=%s&APPID=%s", zipCode, ServiceConstants.API_KEY));
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        Type type = new TypeToken<CityWeather>() {}.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        requestListener.onSuccess((CityWeather) gson.fromJson(response.toString(), type));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    if (in != null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void getWeatherByGeoLoc(final int lat, final int lon, final RequestListener requestListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    URL obj = new URL(GET_URL + String.format("weather?lat=%d&lon=%d&APPID=%s", lat, lon, ServiceConstants.API_KEY));
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        Type type = new TypeToken<CityWeather>() {}.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        requestListener.onSuccess((CityWeather) gson.fromJson(response.toString(), type));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    if (in != null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
