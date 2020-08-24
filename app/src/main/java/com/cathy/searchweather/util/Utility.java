package com.cathy.searchweather.util;

import android.text.TextUtils;

import com.cathy.searchweather.db.City;
import com.cathy.searchweather.db.County;
import com.cathy.searchweather.db.Province;
import com.cathy.searchweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static boolean handleProvinceResponce(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allprovinces = new JSONArray(response);
                for (int i = 0; i < allprovinces.length(); i++) {
                    JSONObject provinceObject = allprovinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static boolean handleCityResponce(String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray citys = new JSONArray(response);
                for (int i = 0; i < citys.length(); i++) {
                    JSONObject city = citys.getJSONObject(i);
                    City c = new City();
                    c.setCityCode(city.getInt("id"));
                    c.setCityName(city.getString("name"));
                    c.setProvinceId(provinceId);
                    c.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static boolean handleCountyResponce(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray arr = new JSONArray(response);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(obj.getString("name"));
                    county.setWeatherId(obj.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
