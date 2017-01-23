package com.sanjeevg.weatherinfo;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.widget.TextView;
import android.os.Handler;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
/**
 * Created by sanjeevg on 21/01/17.
 */

public class WeatherDisplay extends Fragment {
    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView alertField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    Handler handler;

    public WeatherDisplay(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_display, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        alertField = (TextView)rootView.findViewById(R.id.alert_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weatherFont.ttf");
        updateWeatherData(new UserLocation(getActivity()).getCity());
    }

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = FetchWeather.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
        new Thread(){
            public void run(){
                final JSONObject json = FetchWeather.getForecastJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderForecastWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void  renderForecastWeather(JSONObject json){
        try{
            String alertString = getAlertText(json);

            alertField.setTypeface(null, Typeface.BOLD);
            alertField.setText(alertString);
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(700); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            alertField.startAnimation(anim);
        }catch(Exception e){
            Log.e("WeatherInfo", "One or more fields not found in the JSON data");
        }
    }

    private String getAlertText(JSONObject json){
        try{
            JSONArray detailsArray = json.getJSONArray("list");
            for(int i = 0; i < detailsArray.length(); i++)
            {
                JSONObject object = detailsArray.getJSONObject(i).getJSONObject("main");
                //Iterate through the forecast elements
                String temperature = object.getString("temp");
                float forecastTemp = Float.valueOf(temperature);
                if(forecastTemp>30){
                    JSONObject parentObject = detailsArray.getJSONObject(i);
                    String dateString = parentObject.getString("dt_txt").split(" ",2)[0];

                    return String.format("High Temperature expected on "+dateString);
                }else if(forecastTemp<5){
                    JSONObject parentObject = detailsArray.getJSONObject(i);
                    String dateString = parentObject.getString("dt_txt").split(" ",2)[0];

                    return String.format("Low Temperature expected on "+dateString);
                }
            }

            return "";
        }catch(Exception e){
            Log.e("WeatherInfo", "One or more fields not found in the JSON data");
        }
        return "";
    }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

        }catch(Exception e){
            Log.e("WeatherInfo", "One or more fields not found in the JSON data");
        }
    }

    public void changeCity(String city){
        updateWeatherData(city);
    }
}
