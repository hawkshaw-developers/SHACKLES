package in.hawkshaw.shackles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import in.hawkshaw.shackles.model.Constant;
import in.hawkshaw.shackles.model.ImageReceiver;
import in.hawkshaw.shackles.model.InstagramApi;
import in.hawkshaw.shackles.model.PreferencesHelper;

public class DashBoard extends AppCompatActivity implements ImageReceiver.ImageReceiverResponse , InstagramApi.RecentMediaAsync{
    public static final String TAG = "Dashboard Log";
    private ImageView imageView;
    private TextView full_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        imageView = findViewById(R.id.profile_pic);
        full_name = findViewById(R.id.full_name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageReceiver imageReceiver = new ImageReceiver(this);
        JSONObject object = PreferencesHelper.getUseraProfileInfoViaJson(getApplicationContext());
        try {
            imageReceiver.execute(object.getString(Constant.PROF_PIC_URL) );
            full_name.setText(object.getString(Constant.INSTA_FULL_NAME) );
        }
        catch (Exception e){

        }

        InstagramApi api = new InstagramApi();
        api.getRecentMediaAsync(PreferencesHelper.getDataAccessToken(getApplicationContext()) , this);

    }


    public void onClick(View view ){
        switch(view.getId()){
            case R.id.upload_insta_image_button:
                Log.d(TAG , "came to insta pic selection click");
                Intent intent = new Intent(this , UploadImageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void imageResponse(Bitmap map) {
        imageView.setImageBitmap(map);
    }

    @Override
    public void getMediaObject(String result) {
        Log.d(TAG , result);
    }
}
