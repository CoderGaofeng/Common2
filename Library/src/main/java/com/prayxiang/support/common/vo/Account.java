package com.prayxiang.support.common.vo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prayxiang.support.router.Router;
import com.prayxiang.support.router.WebClientActivity;

import java.lang.reflect.Type;

public class Account<T extends AndUser> extends AndObject {
    private Context context;
    private T andUser;
    private MediatorLiveData<Runnable> eventBus = new MediatorLiveData<>();
    private Observer<Runnable> event = new Observer<Runnable>() {
        @Override
        public void onChanged(@Nullable Runnable runnable) {
            runnable.run();
        }

    };

    private static Class<? extends AndUser> userClass = AndUser.class;

    private Toast mToast;


    @SuppressLint("StaticFieldLeak")
    private static Account account;

    public static void registerUser(Class<? extends AndUser> userClass) {
        Account.userClass = userClass;
    }

    public void setDefaultToast(Toast toast) {
        this.mToast = toast;
    }

    public static void init(Context context) {
        if (account == null) {
            synchronized (Account.class) {
                if (account == null) {
                    account = new Account(context);
                }
            }
        }
    }


    public static void sendMessage(String message) {
        account.sendMessage(new Runnable() {
            @Override
            public void run() {
                if (account.mToast == null) {
                    account.mToast = Toast.makeText(account.context, message, Toast.LENGTH_SHORT);
                } else {
                    account.mToast.setText(message);
                }
                account.mToast.show();
            }
        });
    }


    public void sendMessage(Runnable runnable) {
        eventBus.postValue(runnable);
    }

    public static Account get() {
        return account;
    }

    public Account(Context context) {
        this.context = context;
        try {
            String andUser = context.getSharedPreferences("account", Context.MODE_PRIVATE)
                    .getString("user", null);
            andUser = new Gson().fromJson(andUser, (Type) userClass);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (andUser == null) {
                try {
                    andUser = (T) userClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("必须要有一个无参构造器");
                }
            }
        }
        eventBus.observeForever(event);
    }


    public boolean isLogin() {
        return andUser.isLogin();
    }

    public T getUser() {
        return andUser;
    }

    public static Context getContext() {
        return account.context;
    }
}
