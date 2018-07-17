package com.prayxiang.support.common.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.prayxiang.support.common.activity.SimpleActivity;
import com.prayxiang.support.common.dialog.TipDialog;
import com.prayxiang.support.common.viewmodel.DataBoundViewModel;
import com.prayxiang.support.photo.AndPhoto;
import com.prayxiang.support.photo.ImageInfo;
import com.prayxiang.support.router.Router;

import java.util.Arrays;

public class MainActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TipDialog dialog = new TipDialog(this);

//        findViewById(R.id.click)
//                .setOnClickListener(view -> {
//                           getViewModel().tip(Tip.TYPE_LOADING, "jiazo");
//
//                            view.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    getViewModel().tip(Tip.TYPE_FAIL,"xxxxxx");
//
//                                }
//                            },3000);
//                        }
//                );

        ImageView imageView = findViewById(R.id.test);
        ImageInfo info = new ImageInfo("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");

        ImageInfo info2 = new ImageInfo(
                "http://img1.imgtn.bdimg.com/it/u=2852854259,2515271574&fm=27&gp=0.jpg");
        ImageInfo info3 = new ImageInfo("http://img4.imgtn.bdimg.com/it/u=3357021395,3491635869&fm=27&gp=0.jpg");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                   AndPhoto.with(info2)
//                           .group(Arrays.asList(info,info2,info3))
//                           .open((ImageView) view);
                Router.create("www.baidu.com").open(view.getContext());
            }
        });

        Glide.with(this).load(info2.imageUrl).into(imageView);




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
