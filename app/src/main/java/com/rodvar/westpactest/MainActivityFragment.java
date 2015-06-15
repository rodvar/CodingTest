package com.rodvar.westpactest;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rodvar.westpactest.backend.WeatherService;
import com.rodvar.westpactest.model.Weather;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Delegates
 */
public class MainActivityFragment extends Fragment implements Observer {

    private static final String NA_TXT = " N/A";
    private View view;
    private WeatherState weatherState = new NoInfoWeatherState();

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Weather weather = new Weather();
        weather.addObserver(this);
        WeatherService.getInstance().init(weather);
        this.callServiceWhenLocationAvailable();
    }

    private void callServiceWhenLocationAvailable() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    MainActivity activity = (MainActivity) MainActivityFragment.this.getActivity();
                    if (activity.hasLocation()) {
                        WeatherService.getInstance().updateWeather(activity, activity.getLastLocation());
                        timer.cancel();
                    }
                } catch (Exception e) {
                    Log.w("WEATHER_REQ", "Failed to call service");
                }
            }
        }, 0l, 1000l);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null) {
            this.view = inflater.inflate(R.layout.fragment_main, container, false);
        }
        return this.view;
    }

    @Override
    public void update(Observable observable, Object data) {
        Weather weather = WeatherService.getInstance().getWeather();
        ((TextView) this.view.findViewById(R.id.whereLbl)).setText(this.getActivity().getResources().getString(R.string.whereLbl).replaceAll(NA_TXT, " " + weather.getLocation()));
        ((TextView) this.view.findViewById(R.id.wheatherLbl)).setText(this.getActivity().getResources().getString(R.string.wheatherLbl).replaceAll(NA_TXT, " " + weather.getDescription()));
        ((TextView) this.view.findViewById(R.id.temperatureLbl)).setText(this.getActivity().getResources().getString(R.string.tempLbl).replaceAll(NA_TXT, " " + weather.getTemperature()));
        ((TextView) this.view.findViewById(R.id.humidityLbl)).setText(this.getActivity().getResources().getString(R.string.humidityLbl).replaceAll(NA_TXT, " " + weather.getHumidity()));
        ((TextView) this.view.findViewById(R.id.windLbl)).setText(this.getActivity().getResources().getString(R.string.windLbl).replaceAll(NA_TXT, " " + weather.getWindSpeed()));
        ((TextView) this.view.findViewById(R.id.pressureLbl)).setText(this.getActivity().getResources().getString(R.string.pressureLbl).replaceAll(NA_TXT, " " + weather.getPressure()));
        this.updateState(weather);
        this.weatherState.update(this.getActivity(), this.view);
    }

    private void updateState(Weather weather) {
        if (weather.isClear())
            this.weatherState = new ClearWeatherState();
        else if (weather.isRainy())
            this.weatherState = new RainyWeatherState();
        else if (weather.isSunny())
            this.weatherState = new SunnyWheatherState();
        else
            this.weatherState = new NoInfoWeatherState();
    }
}
