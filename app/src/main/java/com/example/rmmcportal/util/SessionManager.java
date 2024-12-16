package com.example.rmmcportal.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.rmmcportal.model.UserAccount;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String USER_TYPE = "user_type";
    private static final String STUDENT_NUMBER = "student_number";
    private static final String PASSWORD = "password";

    private static SessionManager instance;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void saveUserAccount(UserAccount userAccount) {
        editor.putString(USER_ID, userAccount.getId());
        editor.putString(FIRST_NAME, userAccount.getFirstName());
        editor.putString(LAST_NAME, userAccount.getLastName());
        editor.putString(EMAIL, userAccount.getEmail());
        editor.putString(USER_TYPE, userAccount.getUserType());
        editor.putString(STUDENT_NUMBER, userAccount.getStudentNumber());
        editor.putString(PASSWORD, userAccount.getPassword());
        editor.apply();
    }

    public UserAccount getUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(preferences.getString(USER_ID, null));
        userAccount.setFirstName(preferences.getString(FIRST_NAME, null));
        userAccount.setLastName(preferences.getString(LAST_NAME, null));
        userAccount.setEmail(preferences.getString(EMAIL, null));
        userAccount.setUserType(preferences.getString(USER_TYPE, null));
        userAccount.setStudentNumber(preferences.getString(STUDENT_NUMBER, null));
        userAccount.setPassword(preferences.getString(PASSWORD, null));
        return userAccount;
    }

    public void clearSession() {
        editor.clear().apply();
    }
}
