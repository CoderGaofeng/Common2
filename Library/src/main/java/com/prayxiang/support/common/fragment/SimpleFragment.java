package com.prayxiang.support.common.fragment;

import android.databinding.ViewDataBinding;

import com.prayxiang.support.common.viewmodel.DataBoundViewModel;

public class SimpleFragment<T extends ViewDataBinding> extends DataBoundFragment<T,DataBoundViewModel> {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Class getViewModelClass() {
        return DataBoundViewModel.class;
    }
}
