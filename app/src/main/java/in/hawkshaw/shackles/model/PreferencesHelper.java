package in.hawkshaw.shackles.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class PreferencesHelper {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String DATA_ACCESS_TOKEN = "access_token";


    public static SharedPreferences getPreferenceReader(String file , Context context){
        return context.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getPreferenceWriter(String file , Context context){
        return context.getSharedPreferences(file,Context.MODE_PRIVATE).edit();
    }

    public static void setUserAccessToken(String accessCode , Context context){
        SharedPreferences.Editor preferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE).edit();
        preferences.putString(ACCESS_TOKEN , accessCode);
        preferences.apply();
    }

    public static String getUserAccessToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE);
         return preferences.getString(ACCESS_TOKEN,null);
    }

    public static  void setDataAccessToken(String accessCode , Context context){
        SharedPreferences.Editor preferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE).edit();
        preferences.putString(DATA_ACCESS_TOKEN , accessCode);
        preferences.apply();
    }

    public static String getDataAccessToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.DATA_ACCESS_TOKEN , null);
    }

    public static  void setUserProfileInfoViaJson(JSONObject object , Context context){
        SharedPreferences.Editor preferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE).edit();
        try {
            preferences.putString(DATA_ACCESS_TOKEN, object.getString(Constant.DATA_ACCESS_TOKEN));
            object = object.getJSONObject("user");
            preferences.putString(Constant.INSTA_FULL_NAME,object.getString(Constant.INSTA_FULL_NAME));
            preferences.putString(Constant.PROF_PIC_URL,object.getString(Constant.PROF_PIC_URL));
            preferences.putString(Constant.INSTA_BIO , object.getString(Constant.INSTA_BIO));
        }
        catch(Exception e){

        }
        preferences.apply();
    }

    public static JSONObject getUseraProfileInfoViaJson(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.DATA_SHARED_PREFERENCE_FILE_NAME , Context.MODE_PRIVATE);
        JSONObject object = new JSONObject();
        try {
            object.put(Constant.PROF_PIC_URL, sharedPreferences.getString(Constant.PROF_PIC_URL, ""));
            object.put(Constant.INSTA_BIO , sharedPreferences.getString(Constant.INSTA_BIO , ""));
            object.put(Constant.INSTA_FULL_NAME , sharedPreferences.getString(Constant.INSTA_FULL_NAME, ""));
        }
        catch(Exception e){

        }
        return object;
    }

}

