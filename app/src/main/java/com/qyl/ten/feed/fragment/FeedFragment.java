package com.qyl.ten.feed.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bluelinelabs.logansquare.LoganSquare;
import com.qyl.ten.common.data.remote.DiagramDataSource;
import com.qyl.ten.detail.actvity.ImageDetailActivity_;
import com.qyl.ten.feed.adapter.FeedAdapter;
import com.qyl.ten.feed.entity.DiagramTimeLine;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by qiuyunlong on 16/4/15.
 */
public class FeedFragment extends PullToRefreshFragment {
    private static final String TAG = FeedFragment.class.getSimpleName();

    private boolean isLoading = false;
    private boolean isloadEnd = false;
    private DiagramDataSource diagramDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FeedAdapter();
        ((FeedAdapter)adapter).setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = ImageDetailActivity_.intent(getContext())
                        .id(((FeedAdapter) adapter).getDataList().get(position).id)

                        .get();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void scrollToEnd() {
        isLoadEnd = true;
        System.out.println(TAG + "qqqqqqq=====scrollToEnd==");
    }

    @Override
    protected void loadMore() {
        if (isLoading || isLoadEnd) {
            return;
        }

        isLoading = true;

        System.out.println(TAG + "==loadmore");
        if (diagramDataSource == null){
            diagramDataSource = DiagramDataSource.getInstance();
        }
        diagramDataSource.getFeedData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiagramTimeLine>() {
                    @Override
                    public void onCompleted() {
                        isLoading = false;
                        setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        setRefreshing(false);
                        System.out.println(TAG + "====" + e.getMessage());
                    }

                    @Override
                    public void onNext(DiagramTimeLine diagramTimeLine) {
                        System.out.println(TAG + "===" + diagramTimeLine.toString());
                        ((FeedAdapter) adapter).update(diagramTimeLine.result);
                    }
                });

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    private DiagramTimeLine getData() {

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
                return timeLine;
            }else {
                throw new IOException("Unexpected code " + response);
            }
        }catch (Exception e){
            System.out.println("qqqqqqqq==" + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }
}
