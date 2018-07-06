package com.prayxiang.support.common.vo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class AndUser extends AndObject implements Parcelable{
    private String avatarUrl;
    private String userName;
    private String phoneNumber;
    private String password;
    private String confirmCode;

    public AndUser() {

    }

    protected AndUser(Parcel in) {
        avatarUrl = in.readString();
        userName = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        confirmCode = in.readString();
    }

    public static final Creator<AndUser> CREATOR = new Creator<AndUser>() {
        @Override
        public AndUser createFromParcel(Parcel in) {
            return new AndUser(in);
        }

        @Override
        public AndUser[] newArray(int size) {
            return new AndUser[size];
        }
    };

    public boolean isLogin() {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarUrl);
        dest.writeString(userName);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(confirmCode);
    }
}
