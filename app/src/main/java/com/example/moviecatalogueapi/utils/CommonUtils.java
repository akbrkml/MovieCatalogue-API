package com.example.moviecatalogueapi.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {

    public static String dateToString(String date) {
        SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date formatter = null;
        try {
            formatter = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new  SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
                .format(formatter);
    }

    public static void setLoading(View view, Boolean value) {
        if (value) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.INVISIBLE);
    }

    public static int numberOfColumns(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
