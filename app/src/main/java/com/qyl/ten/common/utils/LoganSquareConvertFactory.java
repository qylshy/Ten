package com.qyl.ten.common.utils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.typeconverters.TypeConverter;
import com.qyl.ten.feed.entity.DiagramTimeLine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by qiuyunlong on 16/4/18.
 */
public class LoganSquareConvertFactory extends Converter.Factory {

    private LoganSquare loganSquare;

    public static LoganSquareConvertFactory create(){
        return create(new LoganSquare());
    }

    public static LoganSquareConvertFactory create(LoganSquare longanSquare){
        return new LoganSquareConvertFactory(longanSquare);
    }

    public LoganSquareConvertFactory(LoganSquare loganSquare){
        this.loganSquare = loganSquare;
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        Class<?> cls = (Class<?>) type;

        return new LoganSquareResponseBodyConverter(cls);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
