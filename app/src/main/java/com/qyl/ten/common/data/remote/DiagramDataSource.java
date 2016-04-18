package com.qyl.ten.common.data.remote;

import com.qyl.ten.BuildConfig;
import com.qyl.ten.DiagramService;
import com.qyl.ten.common.data.IDataSource;
import com.qyl.ten.common.utils.LoganSquareConvertFactory;
import com.qyl.ten.feed.entity.DiagramTimeLine;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by qiuyunlong on 16/4/18.
 */
public class DiagramDataSource implements IDataSource<DiagramTimeLine> {

    private static DiagramDataSource INSTANCE;

    private DiagramDataSource(){

    }

    public static DiagramDataSource getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DiagramDataSource();
        }
        return INSTANCE;
    }

    @Override
    public List<DiagramTimeLine> getAllData() {
        return null;
    }

    @Override
    public DiagramTimeLine getData() {
        return null;
    }

    public Observable<DiagramTimeLine> getFeedData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(LoganSquareConvertFactory.create())
                .build();

        return retrofit.create(DiagramService.class)
                .getDiagramTimeLine();
    }
}
