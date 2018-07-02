package com.prayxiang.support.common.recyclerview;

import android.support.v7.widget.RecyclerView;

import com.prayxiang.support.common.vo.Loader;


/**
 * Desc:用于RecyclerView加载更多的监听，实现滑动到底部自动加载更多
 */

public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    private Loader loaderMore;

    public LoadMoreScrollListener(Loader more) {
        this.loaderMore = more;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (recyclerView.computeVerticalScrollOffset() != 0 && !recyclerView.canScrollVertically(1) && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            loaderMore.retry();
        }
    }
}
