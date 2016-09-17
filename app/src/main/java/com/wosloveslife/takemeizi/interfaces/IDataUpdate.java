package com.wosloveslife.takemeizi.interfaces;

import java.util.List;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public interface IDataUpdate<T> {
    void updateData(List<T> data, boolean appended);
}
