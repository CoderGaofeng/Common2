package com.prayxiang.support.photo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.prayxiang.support.common.vo.AndObject;

public class ImageInfo extends AndObject implements Parcelable {

    public int width;
    public int height;

    public String thumbUrl;
    public String imageUrl;

    public int originY;

    public int originX;


    public Bitmap bitmap;


    public ImageInfo(ImageView imageView) {

        if (imageView.isDrawingCacheEnabled()) {
            imageView.setDrawingCacheEnabled(true);
        }
        bitmap = imageView.getDrawingCache();
        int location[]= new int[2];
        imageView.getLocationInWindow(location);
        originY = location[1];
        originX = location[0];

        width = imageView.getWidth();
        height = imageView.getHeight();

    }

    public ImageInfo(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected ImageInfo(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        thumbUrl = in.readString();
        imageUrl = in.readString();
        originY = in.readInt();

        originX = in.readInt();

        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel in) {
            return new ImageInfo(in);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(thumbUrl);
        dest.writeString(imageUrl);
        dest.writeInt(originY);

        dest.writeInt(originX);

        dest.writeParcelable(bitmap, flags);
    }

    @Override
    public void onClick(View v) {

    }
}
