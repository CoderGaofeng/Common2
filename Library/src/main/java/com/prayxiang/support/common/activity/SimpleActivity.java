package com.prayxiang.support.common.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prayxiang.support.component.lifecycle.DataBoundViewModel;


public abstract class SimpleActivity<T extends ViewDataBinding> extends DataBoundActivity<T, DataBoundViewModel> {

    public T binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void dispatchActivityCreated(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        super.dispatchActivityCreated(savedInstanceState);
    }

    public abstract @LayoutRes
    int getLayoutId();

    @NonNull
    @Override
    public Class<DataBoundViewModel> getViewModelClass() {
        return DataBoundViewModel.class;
    }

}
