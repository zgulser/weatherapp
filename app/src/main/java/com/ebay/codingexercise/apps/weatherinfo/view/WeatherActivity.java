package com.ebay.codingexercise.apps.weatherinfo.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.CacheReadListener;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.core.service.WeatherBoundService;
import com.ebay.codingexercise.apps.weatherinfo.core.service.dataprovider.DefaultDataProvider;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppActivityMainBinding;
import com.ebay.codingexercise.apps.weatherinfo.view.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public class WeatherActivity extends BaseActivity
                             implements FragmentManager.OnBackStackChangedListener,
                                        SearchFragment.SearchFragmentListener,
                                        RecentsFragment.RecentsFragmentListener{

    private static final String TAG = WeatherActivity.class.getSimpleName();
    private WeatherBoundService weatherBoundService;
    private WeatherAppActivityMainBinding bindingView;
    private boolean isWeatherServiceBound = false;
    @VisibleForTesting public static final String SEARCH_FRAGMENT_TAG = "search";
    @VisibleForTesting public static final String RESULT_FRAGMENT_TAG = "result";
    @VisibleForTesting public static final String RECENTS_FRAGMENT_TAG = "recents";
    @VisibleForTesting public static final String BUNDLE_CITY_WEATHER_KEY = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bindingView = DataBindingUtil.setContentView(this, R.layout.weather_app_activity_main);
        this.getSupportFragmentManager().addOnBackStackChangedListener(this);
        this.addFragment(SearchFragment.newInstance(new Bundle()), SEARCH_FRAGMENT_TAG);
        this.readLastItemIfAny();
        this.bindService();
    }

    @VisibleForTesting
    public int addFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(bindingView.fragmentContainer.getId(), fragment);
        transaction.addToBackStack(tag);
        return transaction.commit();
    }

    @VisibleForTesting
    public boolean bindService(){
        Intent intent = new Intent(this, WeatherBoundService.class);
        return bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onBackStackChanged() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            boolean displayUpArrow = getSupportFragmentManager().getBackStackEntryCount() > 1;
            actionBar.setDisplayHomeAsUpEnabled(displayUpArrow);
            actionBar.setDisplayShowHomeEnabled(displayUpArrow);
            actionBar.setHomeButtonEnabled(displayUpArrow);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            moveTaskToBack(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Query query) {
        createResultFragment(query.getCityWeather());
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @Override
    public int createResultFragment(CityWeather cityWeather){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CITY_WEATHER_KEY, cityWeather);
        return addFragmentWithPop(ResultFragment.newInstance(bundle), RESULT_FRAGMENT_TAG);
    }

    @VisibleForTesting
    public int addFragmentWithPop(Fragment fragment, String tag) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        return addFragment(fragment, tag);
    }

    @Override
    public void searchByCityName(String query, final RequestListener requestListener) {
        KeyboardUtils.hideKeyboard(getCurrentFocus());
        bindingView.progressBar.setVisibility(View.VISIBLE);
        if (isWeatherServiceBound){
            weatherBoundService.getWeatherByCityName(query, new CustomRequestListener(requestListener));
        } else {
            Log.w(TAG,"WeatherActivity.searchByCityName: WeatherBoundService is not bound!");
        }
    }

    @Override
    public void searchByCityZipCode(String query, final RequestListener requestListener) {
        KeyboardUtils.hideKeyboard(getCurrentFocus());
        bindingView.progressBar.setVisibility(View.VISIBLE);
        if (isWeatherServiceBound){
            weatherBoundService.getWeatherByZipCode(query, new CustomRequestListener(requestListener));
        } else {
            Log.w(TAG,"WeatherActivity.searchByCityZipCode: WeatherBoundService is not bound!");
        }
    }

    @Override
    public void searchByCityGeoloc(int lat, int lon, final RequestListener requestListener) {
        KeyboardUtils.hideKeyboard(getCurrentFocus());
        bindingView.progressBar.setVisibility(View.VISIBLE);
        if (isWeatherServiceBound){
            weatherBoundService.getWeatherByGeoLoc(lat, lon, new CustomRequestListener(requestListener));
        } else {
            Log.w(TAG,"WeatherActivity.searchByCityGeoloc: WeatherBoundService is not bound!");
        }
    }

    @Override
    public int openRecentsFragment() {
        return addFragment(RecentsFragment.newInstance(new Bundle()), RECENTS_FRAGMENT_TAG);
    }

    private ServiceConnection weatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            WeatherBoundService.WeatherServiceBinder binder = (WeatherBoundService.WeatherServiceBinder) service;
            weatherBoundService = binder.getService();
            weatherBoundService.setDataProvider(new DefaultDataProvider());
            isWeatherServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isWeatherServiceBound = false;
        }
    };

    private void readLastItemIfAny(){
        SearchDiskCache.getInstance().readLastObject(this, new CacheReadListener() {
            @Override
            public void onSuccess(List<Query> queryList) {
                if (!queryList.isEmpty()){
                    Query lastQuery = queryList.get(0);
                    createResultFragment(lastQuery.getCityWeather());
                }
            }

            @Override
            public void onError() {
                // do nothing
            }
        });
    }

    /**
     * Helper class to reduce code line
     */
    protected class CustomRequestListener implements RequestListener {

        private RequestListener requestListener;

        public CustomRequestListener(RequestListener requestListener) {
            this.requestListener = requestListener;
        }

        @Override
        public void onSuccess(CityWeather cityWeather) {
            bindingView.progressBar.setVisibility(View.INVISIBLE);
            createResultFragment(cityWeather);
        }

        @Override
        public void onError(RequestError requestError) {
            bindingView.progressBar.setVisibility(View.INVISIBLE);
            requestListener.onError(requestError);
        }
    }
}