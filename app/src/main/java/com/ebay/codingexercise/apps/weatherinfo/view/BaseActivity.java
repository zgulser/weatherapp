package com.ebay.codingexercise.apps.weatherinfo.view;

import android.support.v7.app.AppCompatActivity;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;

/**
 * Created by Zeki Gulser on 01/06/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void createResultFragment(CityWeather cityWeather);

}
