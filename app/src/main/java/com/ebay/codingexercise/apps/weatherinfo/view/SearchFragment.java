package com.ebay.codingexercise.apps.weatherinfo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.CityWeather;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;
import com.ebay.codingexercise.apps.weatherinfo.core.listeners.RequestListener;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppLatlonSearchViewBinding;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppSearchFragmentBinding;
import com.ebay.codingexercise.apps.weatherinfo.view.custom.error.ErrorItem;
import com.ebay.codingexercise.apps.weatherinfo.view.custom.error.StatusView;
import com.ebay.codingexercise.apps.weatherinfo.view.util.KeyboardUtils;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class SearchFragment extends Fragment{

    private static final int DURATION = 400;
    private static final float FROM_SCALE = 0.0f;
    private static final float TO_SCALE = 1.0f;
    private WeatherAppSearchFragmentBinding bindingView;
    private WeatherAppLatlonSearchViewBinding bindingViewLatLonSearch;
    private StatusView errorView;
    private SearchFragmentListener searchFragmentListener;

    interface SearchFragmentListener {
        void searchByCityName(String query, RequestListener requestListener);
        void searchByCityZipCode(String query, RequestListener requestListener);
        void searchByCityGeoloc(int lat, int lon, RequestListener requestListener);
        int openRecentsFragment();
    }

    public static SearchFragment newInstance(Bundle bundle) {
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener){
            searchFragmentListener = (SearchFragmentListener)context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.bindingView = DataBindingUtil.inflate(inflater, R.layout.weather_app_search_fragment, container, false);
        this.bindingView.setClickHandlers(this);
        return bindingView.getRoot();
    }

    public void onClickSearchByName(View view){
        searchFragmentListener.searchByCityName(
                bindingView.nameEditText.getText().toString(), new RequestListener() {
                    @Override
                    public void onSuccess(CityWeather cityWeather) {
                        // do nothing
                    }

                    @Override
                    public void onError(RequestError requestError) {
                        addStatusView(ErrorItem.ErrorType.ERROR);
                    }
                });
    }

    public void onClickSearchByZipCode(View view){
        searchFragmentListener.searchByCityZipCode(
            bindingView.zipEditText.getText().toString(), new RequestListener() {
                @Override
                public void onSuccess(CityWeather cityWeather) {
                    // do nothing
                }

                @Override
                public void onError(RequestError requestError) {
                    addStatusView(ErrorItem.ErrorType.ERROR);
                }
            });
    }

    public void onRecentsClicked(View view){
        searchFragmentListener.openRecentsFragment();
    }

    public void onOpenLatLonSearch(View view){
        if (bindingViewLatLonSearch == null){
            initLatLonSearchView();
        }
        if (bindingViewLatLonSearch.latlonContainer.getVisibility() == View.GONE){
            showLatlonSearchView();
        } else {
            hideLatlonSearchView();
        }
    }

    public void onClickSearchByGeoloc(View view){
        searchFragmentListener.searchByCityGeoloc(
                Integer.valueOf(bindingViewLatLonSearch.latitude.getText().toString()),
                Integer.valueOf(bindingViewLatLonSearch.longitude.getText().toString()), new RequestListener() {
                    @Override
                    public void onSuccess(CityWeather cityWeather) {
                        // do nothing
                    }

                    @Override
                    public void onError(RequestError requestError) {
                        addStatusView(ErrorItem.ErrorType.ERROR);
                    }
                });
    }

    private void initLatLonSearchView(){
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(bindingView.nameZipContainer.getLayoutParams());
        params.width = bindingView.nameZipContainer.getWidth();
        params.topToBottom = bindingView.openLatLonSearch.getId();
        params.setMargins(0, (int)getResources().getDimension(R.dimen.weather_app_openlatlon_margin_top), 0, 0);
        this.bindingViewLatLonSearch = DataBindingUtil.inflate(LayoutInflater.from(bindingView.viewContainer.getContext()),
                        R.layout.weather_app_latlon_search_view, null, false);
        this.bindingViewLatLonSearch.setClickHandlers(this);
        View latLonSearchView = bindingViewLatLonSearch.latlonContainer;
        latLonSearchView.setLayoutParams(params);
        bindingView.viewContainer.addView(latLonSearchView);
        latLonSearchView.setScaleX(FROM_SCALE);
        latLonSearchView.setScaleY(FROM_SCALE);
        latLonSearchView.setVisibility(View.GONE);
    }

    private void addStatusView(ErrorItem.ErrorType errorType){
        if (errorView == null){
            this.errorView = new StatusView(getContext());
        }else {
            removeStatusView();
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        errorView.setLayoutParams(params);
        errorView.show(errorType);
        errorView.getRetryButton().setVisibility(View.VISIBLE);
        errorView.getRetryButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStatusView();
            }
        });
        bindingView.parentView.addView(errorView);
        bindingView.contentContainer.setVisibility(View.GONE);
    }

    private void removeStatusView() {
        if (errorView != null) {
            this.bindingView.parentView.removeView(errorView);
            bindingView.contentContainer.setVisibility(View.VISIBLE);
        }
    }

    private void showLatlonSearchView(){
        AnimatorSet scaler = new AnimatorSet();
        ObjectAnimator scaleX= ObjectAnimator.ofFloat(bindingViewLatLonSearch.latlonContainer, "scaleX", TO_SCALE);
        ObjectAnimator scaleY= ObjectAnimator.ofFloat(bindingViewLatLonSearch.latlonContainer, "scaleY", TO_SCALE);
        scaler.playTogether(scaleX, scaleY);
        scaler.setDuration(DURATION);
        scaler.addListener(new CustomAnimationListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                bindingViewLatLonSearch.latlonContainer.setVisibility(View.VISIBLE);
            }
        });
        scaler.start();
        bindingView.openLatLonSearch.setText(getResources().getString(R.string.weather_app_open_latlon_hide_search_view));
    }

    private void hideLatlonSearchView(){
        AnimatorSet scaler = new AnimatorSet();
        ObjectAnimator scaleX= ObjectAnimator.ofFloat(bindingViewLatLonSearch.latlonContainer, "scaleX", FROM_SCALE);
        ObjectAnimator scaleY= ObjectAnimator.ofFloat(bindingViewLatLonSearch.latlonContainer, "scaleY", FROM_SCALE);
        scaler.playTogether(scaleX, scaleY);
        scaler.setDuration(DURATION);
        scaler.addListener(new CustomAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                bindingViewLatLonSearch.latlonContainer.setVisibility(View.GONE);
            }
        });
        scaler.start();
        bindingView.openLatLonSearch.setText(getResources().getString(R.string.weather_app_open_latlon_show_search_view));
    }
}
