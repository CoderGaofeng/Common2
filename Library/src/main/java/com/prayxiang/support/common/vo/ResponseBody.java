package com.prayxiang.support.common.vo;

public class ResponseBody<T> {
    public int code;

    public T data;
    public String message;
    public boolean success;


    public ResponseBody() {

    }

    public boolean isSuccessful() {
        return code == 0;
    }

    public T body() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                "T" + data +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    public ResponseBody(Throwable throwable) {
        code = -1;
        message = throwable + "";
        success = false;

    }
}
