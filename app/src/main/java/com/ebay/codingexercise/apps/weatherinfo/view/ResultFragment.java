package com.ebay.codingexercise.apps.weatherinfo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppResultFragmentBinding;
import com.ebay.codingexercise.apps.weatherinfo.view.custom.TopAlignSuperscriptSpan;
import com.ebay.codingexercise.apps.weatherinfo.view.util.KeyboardUtils;

import static com.ebay.codingexercise.apps.weatherinfo.view.WeatherActivity.BUNDLE_CITY_WEATHER_KEY;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class ResultFragment extends Fragment {

    private static final String TAG = ResultFragment.class.getSimpleName();
    private static final String IMAGE_URL = "http://openweathermap.org/img/w/%s.png";
    private static final String HUMIDITY_UNIT = "%";
    private static final String PRESSURE_UNIT = "hPa";
    private static final String WIND_UNIT = "m/s";
    private WeatherAppResultFragmentBinding bindingView;

    public static ResultFragment newInstance(Bundle bundle) {
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.bindingView = DataBindingUtil.inflate(inflater, R.layout.weather_app_result_fragment, container, false);
        this.initViews();
        return bindingView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(){
        if (getArguments() != null) {
            CityWeather cityWeather = getArguments().getParcelable(BUNDLE_CITY_WEATHER_KEY);
            bindingView.location.setText(cityWeather.getName() + ", " + cityWeather.getSystem().getCountry());
            uploadWeatherIcon(cityWeather.getWeatherList().get(0).getIconCode());
            bindingView.statusTitle.setText(cityWeather.getWeatherList().get(0).getMain());
            bindingView.statusMsg.setText(cityWeather.getWeatherList().get(0).getDescription());
            setTempSpan(cityWeather);
            bindingView.humidityData.setText(HUMIDITY_UNIT + String.valueOf(cityWeather.getMainData().getHumidity()));
            bindingView.pressureData.setText(String.valueOf(cityWeather.getMainData().getHumidity()) + PRESSURE_UNIT);
            bindingView.windData.setText(String.valueOf(cityWeather.getWind().getSpeed()) + WIND_UNIT);
        }
    }

    private void setTempSpan(CityWeather cityWeather){
        String tempValue = String.valueOf(cityWeather.getMainData().getTemperature());
        Spannable tempSpan = new SpannableString(String.valueOf(cityWeather.getMainData().getTemperature())+"C");
        tempSpan.setSpan(new TopAlignSuperscriptSpan(0.5f), tempValue.length(), tempSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bindingView.temperature.setText(tempSpan);
    }

    private void uploadWeatherIcon(String iconCode){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.weather_app_default_status_icon);
        Glide.with(this)
                .applyDefaultRequestOptions(options)
                .load(String.format(IMAGE_URL, iconCode))
                .into(bindingView.icon);
    }
}
