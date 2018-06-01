package com.ebay.codingexercise.apps.weatherinfo.view.adapter.viewmodel;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public class ItemViewModel {

    private Query query;
    private String name;
    private String date;

    public ItemViewModel(Query query) {
        this.query = query;
        this.name = query.getCityWeather().getName();
        Date queryDate = new Date();
        queryDate.setTime(query.getTimestamp());
        this.date = SimpleDateFormat.getDateInstance().format(queryDate);
    }

    public Query getQuery() {
        return query;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
