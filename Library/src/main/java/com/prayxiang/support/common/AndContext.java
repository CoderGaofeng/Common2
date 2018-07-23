package com.prayxiang.support.common;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.prayxiang.support.common.vo.AndObject;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

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
        Timber.plant(new Timber.DebugTree());
    }


    public static Context getContext() {
        return account.context;
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static void windowInset(View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(view.getContext());
            view.setPadding(view.getPaddingLeft(), statusBarHeight + view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
