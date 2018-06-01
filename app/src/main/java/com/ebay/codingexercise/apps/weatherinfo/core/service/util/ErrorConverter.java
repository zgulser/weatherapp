package com.ebay.codingexercise.apps.weatherinfo.core.service.util;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.RequestError;

import okhttp3.ResponseBody;

/**
 * Created by Zeki Gulser R&D B.V on 30/05/2018.
 */
public class ErrorConverter {

    private ErrorConverter() {}

    public static RequestError convertError(int errorCode, String errorMsg){
        return new RequestError(String.valueOf(errorCode), errorMsg);
    }
}
