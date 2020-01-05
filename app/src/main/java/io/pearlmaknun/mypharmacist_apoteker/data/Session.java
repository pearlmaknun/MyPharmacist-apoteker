package io.pearlmaknun.mypharmacist_apoteker.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.pearlmaknun.mypharmacist_apoteker.MainActivity;
import io.pearlmaknun.mypharmacist_apoteker.model.Apotek;
import io.pearlmaknun.mypharmacist_apoteker.model.Profile;

public class Session {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "my-pharmacist-apoteker";

    private static final String IS_LOGIN = "IsLoged";
    private static final String IS_FIRST = "IsFisrt";
    private static final String DEVICE_ID = "DeviceId";
    private static final String VERSION_APP = "VersionApp";
    private static final String TOKEN = "Token";
    public static final String KEY_USER = "user";
    private static final String APOTEK = "Apotek";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setDeviceId(String id) {
        editor.putString(DEVICE_ID, id);
        editor.commit();
    }

    public String getDeviceId() {
        return pref.getString(DEVICE_ID, "");
    }

    public void setVersionApp(String versionApp) {
        editor.putString(VERSION_APP, versionApp);
        editor.commit();
    }

    public String getVersionApp() {
        return pref.getString(VERSION_APP, "");
    }

    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN, "");
    }

    public void createLoginSession(Profile user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        editor.putBoolean(IS_LOGIN, false);
        editor.putBoolean(IS_FIRST, true);

        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public Profile getUser() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, "");
        Profile obj = gson.fromJson(json, Profile.class);
        return obj;
    }

    public void checkLogin() {
        if (isLoggedIn()) {
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin(Boolean v) {
        editor.putBoolean(IS_LOGIN, v);
        editor.commit();
    }

    public boolean isFirst() {
        return pref.getBoolean(IS_FIRST, false);
    }

    public void setIsFisrt(Boolean v) {
        editor.putBoolean(IS_FIRST, v);
        editor.commit();
    }

    public void setPreferenceApotek(String preferenceApotek) {
        editor.putString(APOTEK, preferenceApotek);
        editor.apply();
    }

    public List<Apotek> getPreferenceApotek() {
        Gson gson = new Gson();
        String json = pref.getString(APOTEK, "");
        JSONArray lists;
        List<Apotek> list = new ArrayList<>();
        try {
            lists = new JSONArray(json);
            for (int i = 0; i < lists.length(); i++){
                list.add(new Apotek(lists.getJSONObject(i).getString("apotik_id"), lists.getJSONObject(i).getString("apotik_name")));
            }
        } catch (JSONException ignored) {

        }
        return list;
    }
}
