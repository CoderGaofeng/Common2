package com.prayxiang.support.common.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;

import com.prayxiang.support.common.LiveBus;
import com.prayxiang.support.common.lifecycle.SingleLiveEvent;
import com.prayxiang.support.common.vo.Resource;
import com.prayxiang.support.common.vo.RetryCallback;
import com.prayxiang.support.common.vo.Status;
import com.prayxiang.support.common.BR;
import com.prayxiang.support.common.recyclerview.LoadListener;

/**
 * Created by xianggaofeng on 2018/1/30.
 */

public class DataBoundViewModel extends ViewModel implements RetryCallback, Observable {
    private String title;

    private String key;

    private transient PropertyChangeRegistry mCallbacks;

    private SingleLiveEvent<String> messageEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Tip> tipEvent = new SingleLiveEvent<>();


    public LiveData<String> obtainMessageEvent() {
        return messageEvent;
    }

    public LiveData<Tip> obtainTipEvent() {
        return tipEvent;
    }

    public void tip(int type, String message) {
        tipEvent.postValue(new Tip(type, message));
    }

    public void message(String message) {
        messageEvent.postValue(message);
    }


    @Override
    public void addOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void updateResource(Resource<?> resource) {
        if (resource != null && resource.status == Status.LOADING) {
            setRefreshing(true);
        } else {
            setRefreshing(false);
        }
    }

    private boolean selected = true;
    private boolean checked = true;
    private boolean focused = true;
    private boolean enabled = true;
    private boolean changed = false;
    private boolean refreshing = false;

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

    /**
     * 刷新行为
     */
    @Override
    public void retry() {
        setRefreshing(true);
    }

    /**
     * 首次初始化时行为
     */
    public void start() {
        retry();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
