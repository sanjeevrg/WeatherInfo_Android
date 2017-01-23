package com.sanjeevg.weatherinfo;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import java.util.Locale;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sanjeevg on 21/01/17.
 */

public class UserLocation extends Activity  {

    SharedPreferences prefs;

    public UserLocation(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Set Bangalore as default location
    String getCity(){
        return prefs.getString("city", "Bangalore, IN");
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }

    void setLocation(String country,String city) {
        prefs.edit().putString("country",country).commit();
        prefs.edit().putString("city",city).commit();
    }

    boolean checkValidLocation(String country, String city) {
        Locale[] locales = Locale.getAvailableLocales();
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String countryCode : isoCountryCodes) {
            Locale locale = new Locale("", countryCode);
            String countryName = locale.getDisplayCountry();
            if(countryName.equalsIgnoreCase(country)){
                return true;
            }
        }

        //Country exists
//        boolean cityCheck = checkCity(country,city);
        return  false;
    }

    //Parse local json and check for valid entry
    boolean checkCity(String country,String city){
        if(country.equalsIgnoreCase("India")){
            if(city.equalsIgnoreCase("Bangalore")||city.equalsIgnoreCase(("Delhi")))
                return true;
        }else if(country.equalsIgnoreCase("Finland")){
            if(city.equalsIgnoreCase("Helsinki")||city.equalsIgnoreCase(("Tampere")))
                return true;
        }
//        try
//        {
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
        return false;
    }
//
//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open("listOfCities.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }

    // Set India as default country
    String getCountry(){
        return prefs.getString("country", "India");
    }

    void setCountry(String country){
        prefs.edit().putString("country",country).commit();
    }
}
