package com.jw.xfkplugin;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public static void init(Context context, String appId) {
        settings = context.getSharedPreferences(String.format("%s_preferences",appId), Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static void setString(String key, String val) {
        editor.putString(key, val);
        editor.commit();
    }

    public static String getString(String key, String defVal) {
        return settings.getString(key, defVal);
    }

    public static float getFloat(String key, float defVal) {
        return settings.getFloat(key, defVal);
    }

    public static void setInt(String key, int val) {
        editor.putInt(key, val);
        editor.commit();
    }

    public static void setFloat(String key, float val) {
        editor.putFloat(key, val);
        editor.commit();
    }

    public static int getInt(String key, int defVal) {
        return settings.getInt(key, defVal);
    }

    public static boolean getBool(String key, boolean defVal) {
        return settings.getBoolean(key, defVal);
    }

    public static void setBool(String key, boolean val) {
        editor.putBoolean(key, val);
        editor.commit();
    }
}

