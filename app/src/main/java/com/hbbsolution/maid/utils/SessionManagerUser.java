package com.hbbsolution.maid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hbbsolution.maid.more.viet_pham.model.signin_signup.Data;
import com.hbbsolution.maid.more.viet_pham.view.signin.SignInActivity;

import java.util.HashMap;

/**
 * Created by DOBYNT on 18/02/2017.
 */

public class SessionManagerUser {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "data";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICE = "price";
    public static final String KEY_EVALUATION = "evaluation";
    public static final String KEY_AGE = "age";

    // Constructor
    public SessionManagerUser(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     */
    public void createLoginSession(Data data) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing in pref
        editor.putString(KEY_TOKEN, data.getToken());
        editor.putString(KEY_ID, data.getUser().get_id());
        editor.putString(KEY_USERNAME, data.getUser().getInfo().getUsername());
        editor.putString(KEY_NAME, data.getUser().getInfo().getName());
        editor.putInt(KEY_GENDER, data.getUser().getInfo().getGender());
        editor.putString(KEY_ADDRESS, data.getUser().getInfo().getAddress().getName());
        editor.putString(KEY_LAT, String.valueOf(data.getUser().getInfo().getAddress().getCoordinates().getLat()));
        editor.putString(KEY_LNG, String.valueOf(data.getUser().getInfo().getAddress().getCoordinates().getLng()));
        editor.putString(KEY_PHONE, data.getUser().getInfo().getPhone());
        editor.putString(KEY_EMAIL, data.getUser().getInfo().getEmail());
        editor.putString(KEY_IMAGE, data.getUser().getInfo().getImage());
        editor.putString(KEY_PRICE, String.valueOf(data.getUser().getWork_info().getPrice()));
        editor.putString(KEY_AGE, String.valueOf(data.getUser().getInfo().getAge()));
        editor.putString(KEY_EVALUATION, String.valueOf(data.getUser().getWork_info().getEvaluation_point()));
        // commit changes
        editor.commit();
    }

    // Remove value whose key
    public void removeValue() {
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_ID);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_NAME);
        editor.remove(KEY_GENDER);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_LAT);
        editor.remove(KEY_LNG);
        editor.remove(KEY_PHONE);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_IMAGE);
        editor.remove(KEY_PRICE);
        editor.remove(KEY_AGE);
        editor.commit();
    }

    /**
     * Check login method wil check data login status
     * If false it will redirect data to login page
     * Else won't do anything
     */

    public boolean checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // data is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            return false;
        }
        return true;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> data = new HashMap<String, String>();
        // data name
        data.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        data.put(KEY_ID, pref.getString(KEY_ID, null));
        data.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        data.put(KEY_NAME, pref.getString(KEY_NAME, null));
        data.put(KEY_GENDER, String.valueOf(pref.getInt(KEY_GENDER, 0)));
        data.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        data.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        data.put(KEY_LAT, pref.getString(KEY_LAT, null));
        data.put(KEY_LNG, pref.getString(KEY_LNG, null));
        data.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        data.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        data.put(KEY_PRICE, pref.getString(KEY_PRICE, null));
        data.put(KEY_AGE, pref.getString(KEY_AGE, null));
        data.put(KEY_EVALUATION, pref.getString(KEY_EVALUATION, null));

        // return data
        return data;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
