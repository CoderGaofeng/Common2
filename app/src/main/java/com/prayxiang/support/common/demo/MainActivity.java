package com.prayxiang.support.common.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.prayxiang.support.common.activity.SimpleActivity;
import com.prayxiang.support.common.dialog.TipDialog;
import com.prayxiang.support.common.viewmodel.DataBoundViewModel;
import com.prayxiang.support.common.viewmodel.Tip;

public class MainActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TipDialog dialog = new TipDialog(this);

        findViewById(R.id.click)
                .setOnClickListener(view -> {
                           getViewModel().tip(Tip.TYPE_LOADING, "jiazo");

                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getViewModel().tip(Tip.TYPE_FAIL,"xxxxxx");
                                }
                            },3000);
                        }
                );



    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    public Class getViewModelClass() {
        return DataBoundViewModel.class;
    }
}
