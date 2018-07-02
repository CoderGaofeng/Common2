package com.prayxiang.support.common.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.prayxiang.support.common.vo.Resource;

public abstract class ObservableViewModel<Body, V> extends DataBoundViewModel {


    private MutableLiveData<Body> liveData = new MutableLiveData<>();

    private LiveData<Resource<V>> result;


    public LiveData<Resource<V>> getResult() {
        return result;
    }


    private Body params;

    public ObservableViewModel() {
        result = Transformations.switchMap(liveData, new Function<Body, LiveData<Resource<V>>>() {
            @Override
            public LiveData<Resource<V>> apply(Body input) {
                return onCreateRequestLiveData(input);
            }
        });
        result.observeForever(new Observer<Resource<V>>() {
            @Override
            public void onChanged(@Nullable Resource<V> resource) {
                ObservableViewModel.this.onChange(resource);
            }
        });


    }

    public abstract LiveData<Resource<V>> onCreateRequestLiveData(Body param);

    public abstract void onChange(Resource<V> resource);

    @Override
    public void retry() {
        super.retry();
        notifyParamChanged();
    }


    public void setParams(Body params) {
        this.params = params;
    }

    public Body getParams() {
        return params;
    }

    public void notifyParamChanged() {
        liveData.setValue(getParams());
    }

    public void notifyParamChanged(Body params) {
        setParams(params);
        notifyParamChanged();
    }
}
