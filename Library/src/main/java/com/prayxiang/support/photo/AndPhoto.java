package com.prayxiang.support.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.prayxiang.support.common.vo.AndObject;
import com.prayxiang.support.photo.widget.DragPhotoView;

import java.util.ArrayList;
import java.util.List;

public class AndPhoto extends AndObject implements Parcelable {

    private ArrayList<ImageInfo> group;
    private Bitmap bitmap;
    private ImageInfo currentImageInfo;
    private int currentPosition;
    private int locationX;
    private int locationY;
    private int width;
    private int height;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ArrayList<ImageInfo> getGroup() {
        return group;
    }

    public ImageInfo getCurrentImageInfo() {
        return currentImageInfo;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    private AndPhoto(ImageInfo imageInfo) {
        currentImageInfo = imageInfo;

    }

    protected AndPhoto(Parcel in) {
        group = in.createTypedArrayList(ImageInfo.CREATOR);
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        currentImageInfo = in.readParcelable(ImageInfo.class.getClassLoader());
        currentPosition = in.readInt();
        locationX = in.readInt();
        locationY = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(group);
        dest.writeParcelable(bitmap, flags);
        dest.writeParcelable(currentImageInfo, flags);
        dest.writeInt(currentPosition);
        dest.writeInt(locationX);
        dest.writeInt(locationY);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AndPhoto> CREATOR = new Creator<AndPhoto>() {
        @Override
        public AndPhoto createFromParcel(Parcel in) {
            return new AndPhoto(in);
        }

        @Override
        public AndPhoto[] newArray(int size) {
            return new AndPhoto[size];
        }
    };

    public AndPhoto group(List<ImageInfo> imageInfos) {

        if (imageInfos instanceof ArrayList) {
            this.group = (ArrayList<ImageInfo>) imageInfos;
        } else {
            this.group = new ArrayList<>(imageInfos);
        }
        ImageInfo cur = currentImageInfo;
        for (int i = 0; i < imageInfos.size(); i++) {
            ImageInfo imageInfo = group.get(i);
            if (cur == imageInfo) {
                currentPosition = i;
                break;
            }
        }

        return this;
    }


    public static AndPhoto with(ImageInfo imageInfo) {
        return new AndPhoto(imageInfo);
    }

    public void open(ImageView imageView) {
        if (!imageView.isDrawingCacheEnabled()) {
            imageView.setDrawingCacheEnabled(true);
        }
        bitmap = imageView.getDrawingCache();

        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        locationX = location[0];
        locationY = location[1];
        width = imageView.getWidth();
        height = imageView.getHeight();
        if (group == null) {
            group = new ArrayList<>();
            group.add(currentImageInfo);
        }

        onClick(imageView);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), DragPhotoActivity.class);
        intent.putExtra("AndPhoto", this);
        v.getContext().startActivity(intent);
        ((Activity)v.getContext()).overridePendingTransition(0,0);
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}
