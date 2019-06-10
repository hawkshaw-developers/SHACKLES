package in.hawkshaw.shackles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import in.hawkshaw.shackles.model.Constant;
import in.hawkshaw.shackles.model.InstagramApi;
import in.hawkshaw.shackles.model.PreferencesHelper;

public class LoginActivity extends AppCompatActivity implements OAuthDialogListener,InstagramApi.AsyncResponse{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String accessToken = PreferencesHelper.getUserAccessToken(getApplicationContext());
        boolean alreadyLoggedIn = accessToken != null ? true : false;
        if(alreadyLoggedIn) {
            // show Dashboard
            Intent intent = new Intent(this , DashBoard.class);
            startActivity(intent);

        }
        else{
            setContentView(R.layout.activity_login);
        }

    }

    public void instaLogin(View vew){
        InstagramDialog dialog = new InstagramDialog(LoginActivity.this , Constant.INSTA_LOGIN_URL , this);
        dialog.show();
    }

    @Override
    public void onComplete(String accessToken)  {
        Log.d("success", "onComplete: "+accessToken);
        PreferencesHelper.setUserAccessToken(accessToken , getApplicationContext());
        InstagramApi api =  new InstagramApi();
        api.getDataAccessToken(PreferencesHelper.getUserAccessToken( getApplicationContext() ), this);
    }

    @Override
    public void onError(String error) {
        Log.d("Error","desc"+error);
    }

    @Override
    public String getResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            PreferencesHelper.setUserProfileInfoViaJson(object , getApplicationContext());
            Intent intent = new Intent(this , DashBoard.class);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
