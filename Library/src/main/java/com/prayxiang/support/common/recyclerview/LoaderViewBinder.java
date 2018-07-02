package com.prayxiang.support.common.recyclerview;


import com.prayxiang.support.common.R;
import com.prayxiang.support.common.databinding.ViewBoundLoadingBinding;
import com.prayxiang.support.common.vo.Loader;
import com.prayxiang.support.common.vo.LoaderStatus;
import com.prayxiang.support.recyclerview.DataBoundViewHolder;
import com.prayxiang.support.recyclerview.ViewBound;


/**
 * Created by xianggaofeng on 2017/6/6.
 */
public class LoaderViewBinder extends ViewBound<Loader, ViewBoundLoadingBinding> {

    public LoaderViewBinder() {
        super(R.layout.view_bound_loading);
    }

    @Override
    public void bindItem(DataBoundViewHolder<ViewBoundLoadingBinding> holder, Loader item) {
        super.bindItem(holder, item);
        holder.binding.setData(item);
    }

    @Override
    public void onViewDetachedFromWindow(DataBoundViewHolder<ViewBoundLoadingBinding> viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        Loader more = viewHolder.getItem();
        if (more.getStatus() != LoaderStatus.GONE) {
            more.setStatus(LoaderStatus.DEFAULT);
        }
    }


}
