package com.rodvar.westpactest;

import android.content.Context;
import android.view.View;

/**
 * Created by rodrigo on 15/06/15.
 */
public interface WeatherState {

    /**
     * Updates app style accordingly
     *
     * @param context
     * @param mainView
     */
    void update(Context context, View mainView);
}
