package com.prayxiang.support.router.route;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.prayxiang.support.router.Utils;
import com.prayxiang.support.router.WebClientActivity;

/**
 * A route tool to open uri by browser
 * Created by lzh on 16/9/5.
 */
public class BrowserRoute implements IRoute {

    Uri uri;

    private Class clz = WebClientActivity.class;
    private static final BrowserRoute route = new BrowserRoute();

    public static BrowserRoute getInstance() {
        return route;
    }

    public <T extends Activity> void registerDefaultWebClient(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public void open(Context context) {
        Intent intent = null;
        if (clz != null) {
            intent = new Intent();
            intent.setClass(context, clz);
            intent.putExtra("uri", uri.toString());
            if (context instanceof Application) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } else {
            intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static boolean canOpenRouter(Uri uri) {
        return Utils.isHttp(uri.getScheme());
    }

    public IRoute setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

}
