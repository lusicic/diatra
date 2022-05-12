package com.unipu.mobapp.diatra.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class LanguageUtils {

    public static void loadLocale(Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(context, language);
    }

    public static void changeLang(Context context, String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(context, lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }

    public static void saveLocale(Context context, String lang) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

}
