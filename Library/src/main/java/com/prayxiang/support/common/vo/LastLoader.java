package com.prayxiang.support.common.vo;

import java.util.Collection;

/**
 * Created by xianggaofeng on 2018/1/27.
 */

public class LastLoader extends Loader {
    private int limit = 0;

    public LastLoader() {

    }
    public void limit(int limit){
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    protected void dispatchStatus(Resource<? extends Collection> resource) {
        Status status = resource.status;
        LoaderStatus loaderStatus;
        if (status == Status.LOADING) {
            loaderStatus = LoaderStatus.LOADING;
        } else if (status == Status.ERROR) {
            loaderStatus = LoaderStatus.ERROR;
        } else if (status == Status.SUCCESS) {
            if (resource.data == null || resource.data.size() <= limit) {
                loaderStatus = LoaderStatus.GONE;
            } else {
                loaderStatus = LoaderStatus.SUCCESS;
            }
        } else {
            loaderStatus = LoaderStatus.DEFAULT;
        }

        setStatus(loaderStatus);

    }

    @Override
    public void retry() {
        LoaderStatus status = getStatus();
        if (status != LoaderStatus.GONE && status != LoaderStatus.LOADING && isEnabled()) {
            if (loadListener != null) {
                setStatus(LoaderStatus.LOADING);
                loadListener.load(currentPage);
            }
        }
    }
}
