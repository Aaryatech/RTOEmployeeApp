package com.ats.rtoemployeeapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ats.rtoemployeeapp.R;
import com.ats.rtoemployeeapp.constants.Constants;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {

    private ZoomageView zoomageView;

    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        zoomageView = findViewById(R.id.myZoomageView);

        try {

            image = Constants.IMAGE_PATH + "" + getIntent().getExtras().getString("image");
            Log.e("IMAGE PATH : ", " " + image);

            Picasso.get().load(image)
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_error)
                    .into(zoomageView);

        } catch (Exception e) {
        }

    }
}
