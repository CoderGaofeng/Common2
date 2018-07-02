package com.prayxiang.support.common.fragment;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prayxiang.support.common.viewmodel.DataBoundViewModel;
import com.prayxiang.support.common.viewmodel.Tip;

public abstract class DataBoundFragment<T extends ViewDataBinding,ViewModel extends DataBoundViewModel> extends BaseFragment {

    public T binding;
    public ViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        if (binding == null) {
            Toast.makeText(getContext(), getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
            return null;
        }
        viewModel = onCreateViewModel();
        if (viewModel != null) {
            viewModel.obtainMessageEvent().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {

                }
            });
            viewModel.obtainTipEvent().observe(this, new Observer<Tip>() {
                @Override
                public void onChanged(@Nullable Tip tip) {
                    if (tip == null) {
                        return;
                    }
                    dispatchTipEvent(tip.type, tip.message);
                }
            });
        }
        return binding.getRoot();
    }


    protected void dispatchTipEvent(int type, String message) {
        obtainDialog().dispatchTipEvent(type,message);
    }
    private ViewModel onCreateViewModel() {
        return null;
    }


    public abstract @LayoutRes
    int getLayoutId();
}
