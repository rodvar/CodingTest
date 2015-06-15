package com.rodvar.westpactest;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Base class for all weather states
 * Created by rodrigo on 15/06/15.
 */
public abstract class BaseWeatherState implements WeatherState {
    protected void changeWeatherColor(View mainView, int color) {
        ((TextView) mainView.findViewById(R.id.wheatherLbl)).setTextColor(color);
    }

    protected int getColor(Context context, int clear) {
        return context.getResources().getColor(R.color.clear);
    }
}
