package com.qyl.ten;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qyl.ten.model.Image;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new ChangeBounds());
            getWindow().setSharedElementExitTransition(new TransitionSet());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(ImageDetailActivity_.intent(getApplicationContext())
                        .image(image)
                        .get());
            }
        });


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

                        ImageLoader.getInstance().displayImage("http://api.shigeten.net/"+image.getImage1(), imageView);
                    }
                });
    }

    private Image getData() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.shigeten.net/api/Diagram/GetDiagramContent?id=10475")
                    .build();
            Response response= okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                Image.Pojo pojo = LoganSquare.parse(response.body().byteStream(), Image.Pojo.class);
                Image image = Image.valueOf(pojo);
                System.out.println("qqqqqqq====" + image.toString());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
