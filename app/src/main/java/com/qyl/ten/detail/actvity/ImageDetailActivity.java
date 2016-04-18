package com.qyl.ten.detail.actvity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qyl.ten.BuildConfig;
import com.qyl.ten.R;
import com.qyl.ten.feed.entity.Image;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @Extra
    protected int id;


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
        //Glide.with(getApplicationContext()).load(BuildConfig.API_HOST + image.getImage1()).into(imageView);
        //simpleDraweeView.setImageURI(Uri.parse(BuildConfig.API_HOST + image.getImage1()));
        //ImageLoader.getInstance().displayImage(BuildConfig.API_HOST + image.getImage1(), imageView);
//        if (Build.VERSION.SDK_INT > 16) {
//            ViewCompat.setTransitionName(imageView, "image_view");
//        }

        Observable.create(new Observable.OnSubscribe<Image>() {
            @Override
            public void call(Subscriber<? super Image> subscriber) {
                image = getData();
                if (subscriber.isUnsubscribed())
                    return;
                System.out.println("qqqqqqqq===call=" + image);
                subscriber.onNext(image);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Image>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("qqqqqqqq===onCompleted=");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("qqqqqqqq===onError=" + e.getMessage());
                    }

                    @Override
                    public void onNext(Image image) {
                        System.out.println("qqqqqqqq===onNext=" + image);

                        //Glide.with(getApplicationContext()).load(BuildConfig.API_HOST + image.getImage1()).into(imageView);
                        //imageView.setImageResource(R.mipmap.ic_launcher);
                        //simpleDraweeView.setImageURI(Uri.parse(BuildConfig.API_HOST + image.getImage1()));
                        ImageLoader.getInstance().displayImage("http://api.shigeten.net/" + image.getImage1(), imageView);

                    }
                });


    }

    private Image getData() {

        //
        // http://api.shigeten.net/api/Diagram/GetDiagramList
        //

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    //.url("http://api.shigeten.net/api/Diagram/GetDiagramList")
                    .url("http://api.shigeten.net/api/Diagram/GetDiagramContent?id=" + id)
                    .build();
            Response response= okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                //DiagramTimeLine timeLine = LoganSquare.parse(response.body().byteStream(), DiagramTimeLine.class);
                Image.Pojo pojo = LoganSquare.parse(response.body().byteStream(), Image.Pojo.class);
                Image image = Image.valueOf(pojo);
                System.out.println("qqqqqqq====" + image.toString());
                //System.out.println("qqqqqq======"+ timeLine.result.get(0));
                //System.out.println("qqqqqqqq====" +response.body().string());
                return image;
            }else {
                throw new IOException("Unexpected code " + response);
            }
        }catch (Exception e){
            System.out.println("qqqqqqqq==" + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21){
            finishAfterTransition();
        }
    }



}
