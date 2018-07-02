package com.prayxiang.support.common.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.prayxiang.support.common.viewmodel.DataBoundViewModel;
import com.prayxiang.support.common.viewmodel.Tip;

public abstract class DataBoundActivity<T extends ViewDataBinding, ViewModel extends DataBoundViewModel> extends BaseActivity {

    public T binding;
    public ViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        viewModel = onCreateViewModel();
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
    }

    protected void dispatchMessageEvent(String message) {
        Toast.makeText(this, message + "", Toast.LENGTH_SHORT).show();
    }

    protected void dispatchTipEvent(int type, String message) {
        obtainDialog().dispatchTipEvent(type, message);
    }


    protected ViewModel onCreateViewModel() {
        Class<? extends ViewModel> cls = getViewModelClass();
        return ViewModelProviders.of(this).get(cls);
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract @NonNull
    Class<? extends ViewModel> getViewModelClass();
}
