package com.qyl.ten.common.utils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.internal.objectmappers.ObjectMapper;
import com.qyl.ten.feed.entity.DiagramTimeLine;
import com.qyl.ten.feed.fragment.PullToRefreshFragment;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by qiuyunlong on 16/4/18.
 */
public class LoganSquareResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Class<T> cls;


    public LoganSquareResponseBodyConverter(Class<T> aClass){
        System.out.println("1111111===构造器"+ aClass.getSimpleName());
        this.cls = aClass;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        T t = LoganSquare.parse(value.byteStream(), cls);
        return t;

//        T t = null;
//        DiagramTimeLine diagramTimeLine = null;
//        try {
//            diagramTimeLine = LoganSquare.parse(value.byteStream(), DiagramTimeLine.class);
//            System.out.println("111111===covert======"+ ",,"+ diagramTimeLine.toString());
//        }catch (Exception e){
//            System.out.println("1111==Exception==" + e.getMessage());
//        }
//
//        //System.out.println("111111===covert---"+ t.getClass() + ",,"+ t.toString());
//        return (T)diagramTimeLine;
    }
}
