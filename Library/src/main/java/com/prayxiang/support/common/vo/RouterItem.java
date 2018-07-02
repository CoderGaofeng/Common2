package com.prayxiang.support.common.vo;

import android.text.TextUtils;
import android.view.View;

import com.prayxiang.support.router.Router;
import com.prayxiang.support.recyclerview.tools.Cell;

public class RouterItem extends BaseObservableObject implements Cell {
    private String title;
    private String thumbUrl;
    private String routerUrl;
    private int type = -1;


    public RouterItem() {

    }

    public RouterItem(String title, String thumbUrl, String routerUrl) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.routerUrl = routerUrl;
    }


    public RouterItem(int type, String title, String thumbUrl, String routerUrl) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.routerUrl = routerUrl;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public void setRouterUrl(String routerUrl) {
        this.routerUrl = routerUrl;
    }


    public String getTitle() {
        return title;
    }

    public String getRouterUrl() {
        return routerUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    @Override
    public int getItemViewType() {
        if (type == -1) {
            return getClass().hashCode();
        }
        return type;
    }

    public void open(View view) {
        if (!TextUtils.isEmpty(routerUrl)) {
            Router.create(getRouterUrl()).open(view.getContext());
        }

    }


}
