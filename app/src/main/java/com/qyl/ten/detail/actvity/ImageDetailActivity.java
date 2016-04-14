package com.qyl.ten.detail.actvity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qyl.ten.BuildConfig;
import com.qyl.ten.R;
import com.qyl.ten.feed.entity.Image;

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

//    @ViewById(R.id.simple_drawee_view)
//    protected SimpleDraweeView simpleDraweeView;

    @Extra
    protected Image image;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        }
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    protected void initViews() {
        //imageView.setImageResource(R.mipmap.ic_launcher);
        System.out.println("qqqqqqqq====" + image);
        Glide.with(getApplicationContext()).load(BuildConfig.API_HOST + image.getImage1()).into(imageView);
        //simpleDraweeView.setImageURI(Uri.parse(BuildConfig.API_HOST + image.getImage1()));
        //ImageLoader.getInstance().displayImage(BuildConfig.API_HOST + image.getImage1(), imageView);
//        if (Build.VERSION.SDK_INT > 16) {
//            ViewCompat.setTransitionName(imageView, "image_view");
//        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21){
            finishAfterTransition();
        }
    }



}
