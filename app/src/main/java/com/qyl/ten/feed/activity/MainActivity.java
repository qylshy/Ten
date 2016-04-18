package com.qyl.ten.feed.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.JsonFactory;
import com.qyl.ten.BuildConfig;
import com.qyl.ten.DiagramService;
import com.qyl.ten.common.utils.LoganSquareConvertFactory;
import com.qyl.ten.detail.actvity.ImageDetailActivity_;
import com.qyl.ten.R;
import com.qyl.ten.feed.entity.DiagramTimeLine;
import com.qyl.ten.feed.entity.Image;
import com.qyl.ten.feed.fragment.FeedFragment;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private Image image;

    private Fragment feedFragment;

    //private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(LoganSquareConvertFactory.create())
                .build();

        final DiagramService diagramService = retrofit.create(DiagramService.class);
//        Observable<DiagramTimeLine> call = diagramService.getDiagramTimeLine();
//        call.
//
//        try {
//            call.enqueue(new Callback<DiagramTimeLine>() {
//                @Override
//                public void onResponse(Call<DiagramTimeLine> call, retrofit2.Response<DiagramTimeLine> response) {
//                    System.out.println("111111=====" + response.isSuccessful());
//                    if (response.isSuccessful()){
//                        DiagramTimeLine diagramTimeLine = null;
//                        try {
//                            diagramTimeLine = response.body();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                            System.out.println("11111111====" + e.getMessage());
//                        }
//                        Toast.makeText(MainActivity.this, "111==" + diagramTimeLine.result.get(0), Toast.LENGTH_LONG)
//                                .show();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<DiagramTimeLine> call, Throwable t) {
//                    System.out.println("111111=====" + t.getMessage());
//                }
//            });
//            //DiagramTimeLine diagramTimeLine = call.execute().body();
//
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("111111111===="+ e.getMessage());
//        }

        //diagramService.getDiagramTimeLine();

        diagramService.getDiagramTimeLine()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiagramTimeLine>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("1111111===onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("111111====" +e.getMessage());
                    }

                    @Override
                    public void onNext(DiagramTimeLine diagramTimeLine) {
                        System.out.println("1111111===onNext=" );
                        Toast.makeText(MainActivity.this, "222==" + diagramTimeLine.result.get(0), Toast.LENGTH_LONG)
                                .show();
                    }
                });


        imageView = (ImageView)findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imageView, "image_view");
               // ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "imageView");


                if (Build.VERSION.SDK_INT > 16){
                    startActivity(ImageDetailActivity_.intent(getApplicationContext())
                            .image(image)
                            .get(), optionsCompat.toBundle());
                }

            }
        });

//        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.simple_drawee_view);
//        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, simpleDraweeView, "simple");
//                // ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "imageView");
//
//
//                if (Build.VERSION.SDK_INT > 16){
//                    startActivity(ImageDetailActivity_.intent(getApplicationContext())
//                            .image(image)
//                            .get(), optionsCompat.toBundle());
//                }
//            }
//        });

        feedFragment = new FeedFragment();
        initFragment(R.id.fragment, feedFragment);


//        Observable.create(new Observable.OnSubscribe<Image>() {
//            @Override
//            public void call(Subscriber<? super Image> subscriber) {
//                image = getData();
//                if (subscriber.isUnsubscribed())
//                    return;
//                System.out.println("qqqqqqqq===call=" + image);
//                subscriber.onNext(image);
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Image>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("qqqqqqqq===onCompleted=");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("qqqqqqqq===onError=" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Image image) {
//                        System.out.println("qqqqqqqq===onNext=" + image);
//
//                        Glide.with(getApplicationContext()).load(BuildConfig.API_HOST + image.getImage1()).into(imageView);
//                        //imageView.setImageResource(R.mipmap.ic_launcher);
//                        //simpleDraweeView.setImageURI(Uri.parse(BuildConfig.API_HOST + image.getImage1()));
//                        //ImageLoader.getInstance().displayImage("http://api.shigeten.net/" + image.getImage1(), imageView);
//
//                    }
//                });
    }

    private Image getData() {

        //
        // http://api.shigeten.net/api/Diagram/GetDiagramList
        //

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.shigeten.net/api/Diagram/GetDiagramList")
                    //.url("http://api.shigeten.net/api/Diagram/GetDiagramContent?id=10475")
                    .build();
            Response response= okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                DiagramTimeLine timeLine = LoganSquare.parse(response.body().byteStream(), DiagramTimeLine.class);
//                Image.Pojo pojo = LoganSquare.parse(response.body().byteStream(), Image.Pojo.class);
//                Image image = Image.valueOf(pojo);
//                System.out.println("qqqqqqq====" + image.toString());
                System.out.println("qqqqqq======"+ timeLine.result.get(0));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21){
            finishAfterTransition();
        }

    }

    protected void initFragment(int container, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.addToBackStack(null);
        ft.commit();
    }
}
