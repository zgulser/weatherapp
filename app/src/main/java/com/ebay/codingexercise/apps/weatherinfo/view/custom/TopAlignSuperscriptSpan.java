package com.ebay.codingexercise.apps.weatherinfo.view.custom;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.SuperscriptSpan;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class TopAlignSuperscriptSpan extends SuperscriptSpan {

    private float shiftPercentage = 0;

    public static final Creator<TopAlignSuperscriptSpan> CREATOR
            = new Creator<TopAlignSuperscriptSpan>() {
        @Override
        public TopAlignSuperscriptSpan createFromParcel(Parcel in) {
            return new TopAlignSuperscriptSpan(in);
        }

        @Override
        public TopAlignSuperscriptSpan[] newArray(int size) {
            return new TopAlignSuperscriptSpan[size];
        }
    };

    public TopAlignSuperscriptSpan(float shiftPercentage) {
        if (shiftPercentage > 0.0 && shiftPercentage < 1.0) {
            this.shiftPercentage = shiftPercentage;
        }
    }

    private TopAlignSuperscriptSpan(Parcel in) {
        this.shiftPercentage = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(shiftPercentage);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        //original ascent
        float ascent = tp.ascent();

        //scale down the font
        int fontScale = 2;
        tp.setTextSize(tp.getTextSize() / fontScale);

        //get the new font ascent
        float newAscent = tp.getFontMetrics().ascent;

        //move baseline to top of old font, then move down size of new font
        //adjust for errors with shift percentage
        tp.baselineShift += (ascent - ascent * shiftPercentage) - (newAscent - newAscent * shiftPercentage);
    }

    @Override
    public void updateMeasureState(TextPaint tp) {
        updateDrawState(tp);
    }
}
