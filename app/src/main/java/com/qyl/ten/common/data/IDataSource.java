package com.qyl.ten.common.data;

import java.util.List;

/**
 * Created by qiuyunlong on 16/4/18.
 */
public interface IDataSource<T> {

    List<T> getAllData();

    T getData();

}
