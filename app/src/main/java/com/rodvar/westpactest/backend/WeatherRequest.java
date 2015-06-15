package com.rodvar.westpactest.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.rodvar.westpactest.model.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Updates weather model with info obtained from:
 * <p/>
 * https://api.forecast.io/forecast/{MINE}/-33.878293,151.2080671
 * Created by rodrigo on 15/06/15.
 */
public class WeatherRequest extends AsyncTask<String, String, JSONObject> {

    private final Weather weather;

    public WeatherRequest(Context context, Weather weather) {
        if (weather == null)
            throw new IllegalArgumentException("Model cannot be null!");
        this.weather = weather;
    }


    @Override
    protected JSONObject doInBackground(String... uri) {
        JSONObject json = null;
        HttpURLConnection urlConnection = null;
        try {
            Log.d("WEATHER_REQ", "Connecting to get weather info at: " + uri);
            URL url = new URL(uri[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("WEATHER_REQ", "Extracting json");
            json = this.extractJson(in);
        } catch (Exception e) {
            Log.e("WEATHER_REQ", "Failed getting info", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return json;
    }

    private JSONObject extractJson(InputStream in) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while (null != (inputStr = streamReader.readLine()))
            responseStrBuilder.append(inputStr);
        return new JSONObject(responseStrBuilder.toString());
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        Log.d("WEATHER_REQ", "Updating model " + json);

        try {
            JSONObject currentWeather = json.getJSONObject("currently");
            this.weather.setLocation(json.getString("timezone"));
            this.weather.setDescription(currentWeather.getString("summary"));
            this.weather.setTemperature(currentWeather.getString("temperature"));
            this.weather.setHumidity(currentWeather.getString("humidity"));
            this.weather.setWindSpeed(currentWeather.getString("windSpeed"));
            this.weather.setPressure(currentWeather.getString("pressure"));

            this.weather.notifyObservers();
        } catch (Exception e) {
            Log.e("WEATHER_REQ", "Failed parsing json", e);
        }
    }
}
