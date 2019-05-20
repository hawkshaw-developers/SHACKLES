package in.hawkshaw.shackles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

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
}
