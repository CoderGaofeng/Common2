package com.prayxiang.support.common.viewmodel;

public final class Tip {
    public static final int TYPE_LOADING = 1;
    /**
     * 显示成功图标
     */
    public static final int TYPE_SUCCESS = 2;
    /**
     * 显示失败图标
     */
    public static final int TYPE_FAIL = 3;
    /**
     * 显示信息图标
     */
    public static final int TYPE_INFO = 4;
    public int type;
    public String message;

    public Tip(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
