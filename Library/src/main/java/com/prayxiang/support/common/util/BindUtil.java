package com.prayxiang.support.common.util;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.prayxiang.support.common.R;
import com.prayxiang.support.common.recyclerview.RecyclerViewBinder;

/**
 * Created by xianggaofeng on 2018/1/30.
 */

public class BindUtil {

    @InverseBindingAdapter(attribute = "refreshing")
    public static boolean isRefreshing(SwipeRefreshLayout view) {
        return view.isRefreshing();
    }

    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
    public static void setOnRefreshListener(final SwipeRefreshLayout view,
                                            final SwipeRefreshLayout.OnRefreshListener listener,
                                            final InverseBindingListener refreshingAttrChanged) {

        SwipeRefreshLayout.OnRefreshListener newValue = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onRefresh();
                }
                if (refreshingAttrChanged != null) {
                    refreshingAttrChanged.onChange();
                }
            }
        };
        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.onRefreshListener);
        if (oldValue != null) {
            view.setOnRefreshListener(null);
        }
        view.setOnRefreshListener(newValue);
    }

    @BindingAdapter(value = {"rvBinder"}, requireAll = false)
    public static void bindRecyclerView(RecyclerView recyclerView, RecyclerViewBinder binder) {
        if (binder != null) {
            binder.onBind(recyclerView);
        }

    }

    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            view.setRefreshing(refreshing);
        }
    }

    @BindingAdapter(value = {"imageUrl", "placeHolder", "errorImage"}, requireAll = false)
    public static void bindImage(ImageView imageView, String url, Drawable drawable, Drawable error) {
        if (url == null) {
            url = "";
        }
        Glide.with(imageView.getContext()).load(url).placeholder(drawable).error(error).into(imageView);
    }
}
