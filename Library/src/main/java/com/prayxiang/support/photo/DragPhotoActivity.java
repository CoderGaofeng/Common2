package com.prayxiang.support.photo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.prayxiang.support.common.R;
import com.prayxiang.support.common.widget.LoadingView;
import com.prayxiang.support.photo.widget.DragPhotoView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.List;

public class DragPhotoActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    AndPhoto andPhoto;
    ViewHolder currentViewHolder;
    private AnimationInfo srcInfo;


    private DragPhotoView.OnExitListener mExitListener = new DragPhotoView.OnExitListener() {
        @Override
        public void onExit(DragPhotoView view, float translateX, float translateY, float w, float h) {
            view.finishAnimationCallBack();

            performExitAnimate(translateX, translateY);
        }
    };


    private DragPhotoView.OnTapListener mTapListener = new DragPhotoView.OnTapListener() {
        @Override
        public void onTap(DragPhotoView view) {
            view.finishAnimationCallBack();
            performFinishAnimate();
        }
    };


    private ArrayList<ViewHolder> mViewHolders;

    private MagicIndicator indicator;
    private List<ImageInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drag_photo);

        mViewPager = findViewById(R.id.viewpager);

        andPhoto = getIntent().getParcelableExtra("AndPhoto");

        srcInfo = new AnimationInfo(andPhoto);
        list = andPhoto.getGroup();
        mViewHolders = new ArrayList<>(list.size());

        for (int i = 0; i < list.size(); i++) {

            mViewHolders.add(new ViewHolder(mViewPager.getContext()));
        }

        indicator = findViewById(R.id.indicator);

        indicator.setVisibility(View.GONE);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentViewHolder = mViewHolders.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOffscreenPageLimit(list.size());
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ViewHolder viewHolder = mViewHolders.get(position);
                View photoView = viewHolder.itemView;
                ImageInfo info = list.get(position);
                if (currentViewHolder != viewHolder) {
                    viewHolder.loadingView.setVisibility(View.VISIBLE);
                    Glide.with(DragPhotoActivity.this).load(info.imageUrl).thumbnail(0.1f).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            viewHolder.loadingView.setVisibility(View.GONE);
                            viewHolder.photoView.setImageDrawable(resource);
                        }
                    });
                }
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        mViewPager.setCurrentItem(andPhoto.getCurrentPosition(), false);




        currentViewHolder = mViewHolders.get(andPhoto.getCurrentPosition());


        mViewPager.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        srcInfo.caculate(currentViewHolder.photoView);


                        for (ViewHolder photoView :
                                mViewHolders) {
                            photoView.photoView.setMinScale(srcInfo.scaleX);
                        }

                        if (andPhoto.getBitmap() != null) {
                            currentViewHolder.photoView.setImageBitmap(andPhoto.getBitmap());
                        }
                        performEnterAnimate();

                    }
                });
    }


    private class ViewHolder {
        private LoadingView loadingView;
        private DragPhotoView photoView;
        private View itemView;

        private ViewHolder(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.common_drag_photo_item, null, false);
            loadingView = view.findViewById(R.id.loading);
            photoView = view.findViewById(R.id.photoView);
            itemView = view;
            photoView.setOnExitListener(mExitListener);
            photoView.setOnTapListener(mTapListener);
        }

        private Context getContext() {
            return itemView.getContext();
        }
    }

    @Override
    public void onBackPressed() {
        performFinishAnimate();
    }


    public class AnimationInfo {
        float originalCenterX;
        float originalCenterY;

        float targetHeight;
        float targetWidth;


        float originalY;
        float originalX;
        float originalWidth;
        float originalHeight;
        float scaleX;
        float scaleY;


        private AnimationInfo(AndPhoto andPhoto) {
            originalWidth = andPhoto.getWidth();
            originalHeight = andPhoto.getHeight();
            originalCenterX = andPhoto.getLocationX() + andPhoto.getWidth() / 2;
            originalCenterY = andPhoto.getLocationY() + andPhoto.getHeight() / 2;
            originalX = andPhoto.getLocationX();
            originalY = andPhoto.getLocationY();
        }

        private void caculate(DragPhotoView photoView) {

            int[] location = new int[2];
            photoView.getLocationOnScreen(location);

            targetWidth = photoView.getWidth();
            targetHeight = photoView.getHeight();
            scaleX = originalWidth / targetWidth;
            scaleY = originalHeight / targetHeight;

        }


        @Override
        public String toString() {
            return "AnimationInfo{" +
                    "originalCenterX=" + originalCenterX +
                    ", originalCenterY=" + originalCenterY +
                    ", originalY=" + originalY +
                    ", originalX=" + originalX +
                    ", originalWidth=" + originalWidth +
                    ", originalHeight=" + originalHeight +
                    '}';
        }
    }


    private void performEnterAnimate() {
        DragPhotoView photoView = currentViewHolder.photoView;
        PropertyValuesHolder scaleXAnimation = PropertyValuesHolder.ofFloat("scaleX", srcInfo.scaleX, 1);
        PropertyValuesHolder dragAlphaAnimation = PropertyValuesHolder.ofInt("dragAlpha", 0, 255);
        PropertyValuesHolder scaleYAnimation = PropertyValuesHolder.ofFloat("scaleY", srcInfo.scaleY, 1);

        int location[] = new int[2];
        photoView.getLocationOnScreen(location);

        float centerX = location[0] + srcInfo.targetWidth / 2;
        float centerY = location[1] + srcInfo.targetHeight / 2;
        float translateX = srcInfo.originalCenterX - centerX;
        float translateY = srcInfo.originalCenterY - centerY;
        photoView.setTranslationY(translateY);
        photoView.setTranslationX(translateX);
        photoView.setScaleY(srcInfo.scaleY);
        photoView.setScaleX(srcInfo.scaleX);
        PropertyValuesHolder translateXAnimation = PropertyValuesHolder.ofFloat("translationX", translateX, 0);
        PropertyValuesHolder translateYAnimation = PropertyValuesHolder.ofFloat("translationY", translateY, 0);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(photoView, scaleXAnimation, scaleYAnimation, translateXAnimation, translateYAnimation, dragAlphaAnimation);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ImageInfo info = andPhoto.getCurrentImageInfo();
                currentViewHolder.loadingView.setVisibility(View.VISIBLE);
                Glide.with(DragPhotoActivity.this).load(info.imageUrl).thumbnail(0.1f).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        currentViewHolder.loadingView.setVisibility(View.GONE);
                        currentViewHolder.photoView.setImageDrawable(resource);
                    }
                });

                if (list.size() > 0) {

                    indicator.setVisibility(View.VISIBLE);
                    CircleNavigator circleNavigator = new CircleNavigator(mViewPager.getContext());
                    circleNavigator.setFollowTouch(false);
                    circleNavigator.setCircleCount(list.size());
                    circleNavigator.setCircleColor(Color.WHITE);
                    circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
                        @Override
                        public void onClick(int index) {
                            mViewPager.setCurrentItem(index);
                        }
                    });

                    circleNavigator.notifyDataSetChanged();
                    indicator.setNavigator(circleNavigator);
                    ViewPagerHelper.bind(indicator, mViewPager);
                    circleNavigator.onPageSelected(andPhoto.getCurrentPosition());
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();


    }


    private void performFinishAnimate() {
        DragPhotoView photoView = currentViewHolder.photoView;

        PropertyValuesHolder scaleXAnimation = PropertyValuesHolder.ofFloat("scaleX", 1, srcInfo.scaleX);
        PropertyValuesHolder dragAlphaAnimation = PropertyValuesHolder.ofInt("dragAlpha", 255, 0);
        PropertyValuesHolder scaleYAnimation = PropertyValuesHolder.ofFloat("scaleY", 1, srcInfo.scaleY);

        int location[] = new int[2];
        photoView.getLocationOnScreen(location);

        float centerX = location[0] + srcInfo.targetWidth / 2;
        float centerY = location[1] + srcInfo.targetHeight / 2;
        float translateX = srcInfo.originalCenterX - centerX;
        float translateY = srcInfo.originalCenterY - centerY;

        PropertyValuesHolder translateXAnimation = PropertyValuesHolder.ofFloat("translationX", 0, translateX);
        PropertyValuesHolder translateYAnimation = PropertyValuesHolder.ofFloat("translationY", 0, translateY);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(photoView, scaleXAnimation, scaleYAnimation, translateXAnimation, translateYAnimation, dragAlphaAnimation);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();


    }

    private void performExitAnimate(float x, float y) {
        DragPhotoView view = currentViewHolder.photoView;
        view.finishAnimationCallBack();
        float viewX = srcInfo.targetWidth / 2 + x - srcInfo.targetHeight * srcInfo.scaleX / 2;
        float viewY = srcInfo.targetHeight / 2 + y - srcInfo.targetHeight * srcInfo.scaleY / 2;
        view.setX(viewX);
        view.setY(viewY);

        float centerX = view.getX() + srcInfo.originalWidth / 2;
        float centerY = view.getY() + srcInfo.originalHeight / 2;

        float translateX = srcInfo.originalCenterX - centerX;
        float translateY = srcInfo.originalCenterY - centerY;


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), view.getX() + translateX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), view.getY() + translateY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();


    }

}
