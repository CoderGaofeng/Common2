package com.prayxiang.support.common.fragment;

import android.databinding.ViewDataBinding;

import com.prayxiang.support.common.viewmodel.DataBoundViewModel;

public abstract class SimpleFragment<T extends ViewDataBinding> extends DataBoundFragment<T,DataBoundViewModel> {



    @Override
    public Class getViewModelClass() {
        return DataBoundViewModel.class;
    }
}
