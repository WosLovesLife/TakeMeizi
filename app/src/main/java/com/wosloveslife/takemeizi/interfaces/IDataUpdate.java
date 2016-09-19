package com.wosloveslife.takemeizi.interfaces;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public interface IDataUpdate<T> {
    void onUpdateData(T data, boolean appended);
}
