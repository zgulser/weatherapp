package com.ebay.codingexercise.apps.weatherinfo.view.custom.error;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebay.codingexercise.apps.weatherinfo.R;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public abstract class BaseStatusView extends LinearLayout {

    private ImageView errorImageView;
    private TextView titleTextView;
    private TextView messageTextView;
    private Button retryButton;

    public BaseStatusView(Context context) {
        this(context, null);
    }

    public BaseStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.inflateViews();
        this.retryButton.setVisibility(View.GONE);
    }

    private void inflateViews() {
        inflate(getContext(), R.layout.weather_app_error_view, this);
        this.errorImageView = findViewById(R.id.icon);
        this.titleTextView = findViewById(R.id.title);
        this.messageTextView = findViewById(R.id.message);
        this.retryButton = findViewById(R.id.retry);
    }

    public void show(ErrorItem.ErrorType errorType) {
        ErrorItem errorItem = getErrorItem(errorType);
        this.errorImageView.setImageResource(errorItem.getImageResource());
        this.titleTextView.setText(errorItem.getTitle());
        this.messageTextView.setText(errorItem.getMessage());
        this.setVisibility(VISIBLE);
    }

    public void hide() {
        this.setVisibility(GONE);
    }

    public Button getRetryButton() {
        return retryButton;
    }

    /**
     * Abstract method for creation of ErrorItem object.
     *
     * @param errorType Object of type ErrorType.
     * @return ErrorItem object for the given ErrorType.
     */
    protected abstract ErrorItem getErrorItem(ErrorItem.ErrorType errorType);
}
