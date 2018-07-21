package com.prayxiang.support.common.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.prayxiang.support.component.lifecycle.DataBoundViewModel;


public abstract class DataBoundActivity<T extends ViewDataBinding, ViewModel extends DataBoundViewModel> extends ViewModelActivity<ViewModel> {

    public T binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void dispatchActivityCreated(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        super.dispatchActivityCreated(savedInstanceState);
    }


    public abstract @LayoutRes
    int getLayoutId();
}
