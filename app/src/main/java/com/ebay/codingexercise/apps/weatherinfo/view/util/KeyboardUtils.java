package com.ebay.codingexercise.apps.weatherinfo.view.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Zeki Gulser on 01/06/2018.
 */

public class KeyboardUtils {

    public static void hideKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
