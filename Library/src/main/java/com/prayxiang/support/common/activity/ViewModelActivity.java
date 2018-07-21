package com.prayxiang.support.common.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.prayxiang.support.component.lifecycle.DataBoundViewModel;
import com.prayxiang.support.component.lifecycle.Tip;

public abstract class ViewModelActivity<ViewModel extends DataBoundViewModel> extends BaseActivity {

    private ViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewModel != null) {
            viewModel.obtainMessageEvent().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    dispatchMessageEvent(s);
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
        viewModel = onCreateViewModel();
        dispatchActivityCreated(savedInstanceState);

    }

    protected void dispatchActivityCreated(@Nullable Bundle savedInstanceState) {
        onActivityCreated(savedInstanceState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    protected void dispatchMessageEvent(String message) {
        Toast.makeText(this, message + "", Toast.LENGTH_SHORT).show();
    }

    protected void dispatchTipEvent(int type, String message) {
        obtainDialog().dispatchTipEvent(type, message);
    }


    public ViewModel getViewModel() {
        return viewModel;
    }


    protected ViewModel onCreateViewModel() {
        Class<ViewModel> cls = getViewModelClass();
        return ViewModelProviders.of(this).get(cls);
    }

    public abstract @NonNull
    Class<ViewModel> getViewModelClass();
}
