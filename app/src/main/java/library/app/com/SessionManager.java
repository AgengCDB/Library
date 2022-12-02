package library.app.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {

    //Put Shared Preference (For Session Management)
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID_USER = "id_user";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String name, String email, String phone) {
        editor.putBoolean(LOGIN, true);
        editor.putString(ID_USER, id);
        editor.putString(USERNAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PHONE, phone);
        editor.apply();
    }

    public boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {

        if(!this.isLoggin()) {
            Intent i = new Intent(context.getApplicationContext(), LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }
    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(ID_USER, sharedPreferences.getString(ID_USER, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PHONE, sharedPreferences.getString(PHONE, null));

//        Log.e("testJ", ID_USER);

        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
}
