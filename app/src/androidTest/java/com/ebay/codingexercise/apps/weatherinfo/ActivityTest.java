package com.ebay.codingexercise.apps.weatherinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.view.ResultFragment;
import com.ebay.codingexercise.apps.weatherinfo.view.WeatherActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest {

    @Rule
    public ActivityTestRule<WeatherActivity> activityTestRule =
            new ActivityTestRule(WeatherActivity.class);

    @Test
    public void test_addFragment() {
        CityWeather cityWeather = getCityWeather("cityweather");
        Bundle bundle = new Bundle();
        bundle.putParcelable(WeatherActivity.BUNDLE_CITY_WEATHER_KEY, cityWeather);
        int result = activityTestRule.getActivity().addFragment(
                ResultFragment.newInstance(bundle),
                WeatherActivity.RESULT_FRAGMENT_TAG);
        assertTrue("@test_addFragment: unable to add a fragment", result > 0);
    }

    @Test
    public void test_bindService() {
        boolean connected = activityTestRule.getActivity().bindService();
        assertTrue("@test_bindService: Unable to bind to the weather service", connected);
    }

    @Test
    public void test_openRecentsFragment(){
        int backStackEntry = activityTestRule.getActivity().openRecentsFragment();
        assertTrue("@test_openRecentsFragment: Unable to open recents fragment", backStackEntry > 0);
    }

    @Test
    public void test_createResultFragment(){
        CityWeather cityWeather = getCityWeather("cityweather");
        int backStackEntry = activityTestRule.getActivity().createResultFragment(cityWeather);
        assertTrue("@test_createResultFragment: Unable to create a result fragment", backStackEntry > 0);
    }

    private CityWeather getCityWeather(String fileName) {
        String result = "";
        String file = String.format("test/%s.json", fileName);
        InputStream bis = null;
        try {
            bis = new BufferedInputStream(
                    InstrumentationRegistry.getInstrumentation().getContext().getAssets().open(file));
            byte[] buf = new byte[512];
            int read = 0;
            while ((read = bis.read(buf)) != -1) {
                result += new String(Arrays.copyOfRange(buf, 0, read), "UTF-8");
                ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Type portfolioListType = new TypeToken<CityWeather>() {
        }.getType();
        return new Gson().fromJson(result, portfolioListType);
    }
}
