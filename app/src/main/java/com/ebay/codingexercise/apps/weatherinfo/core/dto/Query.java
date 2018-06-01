package com.ebay.codingexercise.apps.weatherinfo.core.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class Query implements Serializable, Parcelable {

    @SerializedName("query")
    private String query;

    @SerializedName("cityWeather")
    private CityWeather cityWeather;

    @SerializedName("timestamp")
    private long timestamp;

    public Query(String query, CityWeather cityWeather, long timestamp) {
        this.query = query;
        this.cityWeather = cityWeather;
        this.timestamp = timestamp;
    }

    protected Query(Parcel in) {
        this.query = in.readString();
        this.cityWeather = in.readParcelable(CityWeather.class.getClassLoader());
        this.timestamp = in.readLong();
    }

    public static final Parcelable.Creator<Query> CREATOR = new Parcelable.Creator<Query>() {
        @Override
        public Query createFromParcel(Parcel source) {
            return new Query(source);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[size];
        }
    };

    public String getQuery() {
        return query;
    }

    public CityWeather getCityWeather() {
        return cityWeather;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.query);
        dest.writeParcelable(this.cityWeather, flags);
        dest.writeLong(this.timestamp);
    }
}
