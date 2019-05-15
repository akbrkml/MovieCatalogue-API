package com.example.moviecatalogueapi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private static SharedPreferences getPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putString(Context context, String key, String value) {
        getPref(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return getPref(context).getString(key, "");
    }

    public static void putInt(Context context, String key, int value) {
        getPref(context).edit().putInt(key, value).apply();
    }

    public static Integer getInt(Context context, String key) {
        return getPref(context).getInt(key, 0);
    }

    public static void putLong(Context context, String key, long value) {
        getPref(context).edit().putLong(key, value).apply();
    }

    public static Long getLong(Context context, String key) {
        return getPref(context).getLong(key, 0);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        getPref(context).edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(Context context, String key) {
        return getPref(context).getBoolean(key, false);
    }

    public static void clearAll(Context context) {
        getPref(context).edit().clear().apply();
    }

    public static void clear(Context context, String key){
        getPref(context).edit().remove(key).apply();
    }

}
