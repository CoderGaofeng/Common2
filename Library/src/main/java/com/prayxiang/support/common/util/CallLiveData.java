package com.prayxiang.support.common.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.prayxiang.support.common.vo.AndResponse;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallLiveData<R> extends LiveData<AndResponse<R>> {
    final Call<R> call;
    AtomicBoolean started = new AtomicBoolean(false);
    AndResponse<R> mData;

    public CallLiveData(Call<R> call) {
        this.call = call;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (started.compareAndSet(false, true)) {
            call.enqueue(new Callback<R>() {
                @Override
                public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                    postValue(new AndResponse<>(response));
                }

                @Override
                public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                    postValue(new AndResponse<>(throwable));
                }
            });
        }
    }

    @WorkerThread
    public AndResponse<R> execute() {
        AndResponse<R> andResponse = mData;
        if (started.compareAndSet(false, true)) {
            try {
                andResponse = new AndResponse<>(call.execute());
                return andResponse;
            } catch (Exception e) {
                e.printStackTrace();
                andResponse = new AndResponse<>(e);
            }
            mData = andResponse;
        } else if (mData == null) {
            throw new RuntimeException("not found data");
        }
        return andResponse;

    }
}
