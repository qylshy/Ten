package com.qyl.ten;

import com.qyl.ten.feed.entity.DiagramTimeLine;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by qiuyunlong on 16/4/18.
 */
public interface DiagramService {

    @GET("/api/Diagram/GetDiagramList")
    Observable<DiagramTimeLine> getDiagramTimeLine();
}
