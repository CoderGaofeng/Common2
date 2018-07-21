package com.prayxiang.support.component.lifecycle;

import android.arch.lifecycle.LiveData;

import com.prayxiang.support.common.vo.Resource;


/**
 * Created by xianggaofeng on 2018/1/27.
 */

public class ErrorLiveData extends LiveData {
    private ErrorLiveData() {
        postValue(Resource.error("",null));
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new ErrorLiveData();
    }
}
