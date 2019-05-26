package in.hawkshaw.shackles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import in.hawkshaw.shackles.model.Constant;
import in.hawkshaw.shackles.model.InstagramApi;

public class LoginActivity extends AppCompatActivity implements OAuthDialogListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean alreadyLoggedIn = false;
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
        InstagramApi api = new InstagramApi();
        String accessToken ="de6d5f0476244f70a60f91da4f16461e";

        api.execute(accessToken,accessToken,accessToken);

        //InstagramDialog dialog = new InstagramDialog(LoginActivity.this , Constant.INSTA_LOGIN_URL , this);
        Log.d("dialog","error");
       // dialog.show();

    }

    @Override
    public void onComplete(String accessToken)  {
        Log.d("success", "onComplete: "+accessToken);



    }

    @Override
    public void onError(String error) {
        Log.d("Error","desc"+error);
    }
}
