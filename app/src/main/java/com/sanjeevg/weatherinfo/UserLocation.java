package com.sanjeevg.weatherinfo;

import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Locale;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sanjeevg on 21/01/17.
 */

public class UserLocation  {
    SharedPreferences prefs;

    public UserLocation(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // If the user has not chosen a city yet, return
    // Sydney as the default city
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
        //boolean cityCheck = checkCity(country,city);
        return  false;
    }

//    boolean checkCity(String country,String city){
//        try
//        {
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//
//            JSONArray jarray = (JSONArray) obj.getJSONArray(country);
//            for(int i=0;i<jarray.length();i++)
//            {
//                JSONObject jb =(JSONObject) jarray.get(i);
//                String formula = jb.getString("formule");
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = this.getActivity().getAssets().open("listOfCities.json");
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

    String getCountry(){
        return prefs.getString("country", "India");
    }

    void setCountry(String country){
        prefs.edit().putString("country",country).commit();
    }
}
