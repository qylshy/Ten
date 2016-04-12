package com.qyl.ten;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qyl.ten.model.Image;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by qiuyunlong on 16/4/11.
 */
@EActivity(R.layout.activity_detail)
public class ImageDetailActivity extends AppCompatActivity {

    @ViewById(R.id.image)
    protected ImageView imageView;

    @Extra
    protected Image image;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    protected void initViews() {
        ImageLoader.getInstance().displayImage(BuildConfig.API_HOST + image.getImage1(), imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
