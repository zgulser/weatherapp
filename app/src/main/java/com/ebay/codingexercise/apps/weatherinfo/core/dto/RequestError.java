package com.ebay.codingexercise.apps.weatherinfo.core.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class RequestError implements Serializable, Parcelable {

    @SerializedName("cod")
    private String code;

    @SerializedName("message")
    private String message;

    public static final Parcelable.Creator<RequestError> CREATOR = new Parcelable.Creator<RequestError>() {
        @Override
        public RequestError createFromParcel(Parcel source) {
            return new RequestError(source);
        }

        @Override
        public RequestError[] newArray(int size) {
            return new RequestError[size];
        }
    };

    public RequestError() {
    }

    public RequestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    protected RequestError(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
    }
}
