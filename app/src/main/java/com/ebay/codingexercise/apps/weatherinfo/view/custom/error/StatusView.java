package com.ebay.codingexercise.apps.weatherinfo.view.custom.error;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppLatlonSearchViewBinding;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public class StatusView extends BaseStatusView {


    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * This method will generate an ErrorItem object for the given errorType.
     *
     * @param errorType Object of type ErrorType.
     * @return ErrorItem object.
     */
    @Override
    protected ErrorItem getErrorItem(ErrorItem.ErrorType errorType) {
        int icon;
        String title;
        String message;

        switch (errorType) {
            case EMPTY:
                icon = R.drawable.weather_app_empty_icon;
                title = getContext().getString(R.string.weather_app_empty_title);
                message = getContext().getString(R.string.weather_app_empty_message);
                break;
            case OFFLINE:
                icon = R.drawable.weather_app_offline_icon;
                title = getContext().getString(R.string.weather_app_offline_title);
                message = getContext().getString(R.string.weather_app_offline_message);
                break;
            case ERROR:
            default:
                icon = R.drawable.weather_app_failed_icon;
                title = getContext().getString(R.string.weather_app_error_title);
                message = getContext().getString(R.string.weather_app_error_message);
        }

        return new ErrorItem(icon, title, message);
    }
}
