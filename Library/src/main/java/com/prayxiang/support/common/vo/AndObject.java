package com.prayxiang.support.common.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.prayxiang.support.common.BR;
import com.prayxiang.support.router.Router;

/**
 * Created by xianggaofeng on 2018/1/30.
 */

public class AndObject extends BaseObservable implements View.OnClickListener {
    private transient boolean selected = true;
    private transient boolean checked = true;
    private transient boolean focused = true;
    private transient boolean enabled = true;
    private transient boolean changed = false;
    private transient boolean refreshing = false;
    private transient String routerUri;


    @Bindable
    public boolean isRefreshing() {
        return refreshing;
    }

    @Bindable
    public boolean isChanged() {
        return changed;
    }


    @Bindable
    public boolean isChecked() {
        return checked;
    }

    @Bindable
    public boolean isEnabled() {
        return enabled;
    }

    @Bindable
    public boolean isFocused() {
        return focused;
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        notifyPropertyChanged(BR.refreshing);
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
        notifyPropertyChanged(BR.changed);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        notifyPropertyChanged(BR.enabled);
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
        notifyPropertyChanged(BR.focused);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }


    public String getRouterUri() {
        return routerUri;
    }

    public void setRouterUri(String routerUri) {
        this.routerUri = routerUri;
    }


    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(routerUri)) {
            Router.create(routerUri).open(v.getContext());
        }
    }
}
