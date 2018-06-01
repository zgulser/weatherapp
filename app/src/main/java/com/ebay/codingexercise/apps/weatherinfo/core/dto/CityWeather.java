package com.ebay.codingexercise.apps.weatherinfo.core.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class CityWeather implements Serializable, Parcelable {

    @SerializedName("coord")
    private Coordinate coordinate;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private Main mainData;

    @SerializedName("visibility")
    private long visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private long dt;

    @SerializedName("sys")
    private System system;

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    public static final Parcelable.Creator<CityWeather> CREATOR = new Parcelable.Creator<CityWeather>() {
        @Override
        public CityWeather createFromParcel(Parcel source) {
            return new CityWeather(source);
        }

        @Override
        public CityWeather[] newArray(int size) {
            return new CityWeather[size];
        }
    };

    public CityWeather() {
    }

    protected CityWeather(Parcel in) {
        this.coordinate = in.readParcelable(Coordinate.class.getClassLoader());
        this.weatherList = in.createTypedArrayList(Weather.CREATOR);
        this.base = in.readString();
        this.mainData = in.readParcelable(Main.class.getClassLoader());
        this.visibility = in.readLong();
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.clouds = in.readParcelable(Clouds.class.getClassLoader());
        this.dt = in.readLong();
        this.system = in.readParcelable(System.class.getClassLoader());
        this.id = in.readLong();
        this.name = in.readString();
        this.cod = in.readInt();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMainData() {
        return mainData;
    }

    public void setMainData(Main mainData) {
        this.mainData = mainData;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coordinate, flags);
        dest.writeTypedList(this.weatherList);
        dest.writeString(this.base);
        dest.writeParcelable(this.mainData, flags);
        dest.writeLong(this.visibility);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.clouds, flags);
        dest.writeLong(this.dt);
        dest.writeParcelable(this.system, flags);
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.cod);
    }
}
