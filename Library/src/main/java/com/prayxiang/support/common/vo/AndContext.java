package com.prayxiang.support.common.vo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class AndContext extends AndObject {
    private Context context;

    private MutableLiveData<Runnable> eventBus = new MutableLiveData<>();
    private Observer<Runnable> event = runnable -> runnable.run();
    private Toast mToast;


    @SuppressLint("StaticFieldLeak")
    private volatile static AndContext account;


    public static void attach(Context context) {
        if (account == null) {
            synchronized (AndContext.class) {
                if (account == null) {
                    account = new AndContext(context);
                }
            }
        }
    }


    public void setDefaultToast(Toast toast) {
        this.mToast = toast;
    }


    public void sendMessage(String message) {
        account.sendMessage(new Runnable() {
            @Override
            public void run() {

                if (account.mToast == null) {
                    account.mToast = Toast.makeText(account.context, message, Toast.LENGTH_SHORT);
                } else {
                    account.mToast.cancel();
                    account.mToast.setDuration(Toast.LENGTH_SHORT);
                    account.mToast.setText(message);
                }
                account.mToast.cancel();
                account.mToast.show();
            }
        });
    }


    public void sendMessage(Runnable runnable) {
        eventBus.postValue(runnable);
    }

    public static AndContext get() {
        return account;
    }

    public AndContext(Context context) {
        this.context = context;
        eventBus.observeForever(event);
    }


    public static Context getContext() {
        return account.context;
    }
}
