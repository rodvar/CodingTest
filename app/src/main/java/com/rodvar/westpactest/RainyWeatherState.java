package com.rodvar.westpactest;

import android.content.Context;
import android.view.View;

/**
 * Created by rodrigo on 15/06/15.
 */
public class RainyWeatherState extends BaseWeatherState {
    @Override
    public void update(Context context, View mainView) {
        this.changeWeatherColor(mainView, this.getColor(context, R.color.rainy));
    }
}
