package com.ebay.codingexercise.apps.weatherinfo.view.custom.error;

/**
 * Created by Zeki Gulser R&D B.V on 31/05/2018.
 */
public class ErrorItem {

    public enum ErrorType {
        EMPTY, OFFLINE, ERROR;
    }

    private final int imageResource;
    private final String title;
    private final String message;

    ErrorItem(int imageResource, String title, String message) {
        this.imageResource = imageResource;
        this.title = title;
        this.message = message;
    }

    int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    String getMessage() {
        return message;
    }
}
