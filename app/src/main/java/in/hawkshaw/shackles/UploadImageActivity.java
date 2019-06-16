package in.hawkshaw.shackles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;


import java.util.ArrayList;

import in.hawkshaw.shackles.ImageAdapter;
import in.hawkshaw.shackles.R;
import in.hawkshaw.shackles.model.InstagramApi;
import in.hawkshaw.shackles.model.JsonParserHelper;
import in.hawkshaw.shackles.model.PreferencesHelper;

public class UploadImageActivity extends AppCompatActivity implements InstagramApi.RecentMediaAsync {

    RecyclerView recyclerView;
    ArrayList<String> list = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        recyclerView = findViewById(R.id.image_recycler_view);
        InstagramApi api = new InstagramApi();
        api.getRecentMediaAsync(PreferencesHelper.getDataAccessToken(getApplicationContext()) , this);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void getMediaObject(String result) {
        list = JsonParserHelper.getUrlsFromRecentMedia(result);
        GridLayoutManager manager = new GridLayoutManager(this , 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new ImageAdapter(list , this));

    }
}
