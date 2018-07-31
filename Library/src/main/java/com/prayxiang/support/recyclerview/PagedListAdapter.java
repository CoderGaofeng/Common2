//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.prayxiang.support.recyclerview;

import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prayxiang.support.recyclerview.tools.Cell;

import java.lang.ref.WeakReference;
import java.util.List;

public class PagedListAdapter<T extends Cell> extends android.arch.paging.PagedListAdapter<T, ViewHolder> implements OnRebindCallbackProvider<ViewDataBinding> {

    private WeakReference<RecyclerView> recyclerViewWeakReference;
    static final Object DB_PAYLOAD = new Object();
    final OnRebindCallback mOnRebindCallback = new OnRebindCallback() {
        public boolean onPreBind(ViewDataBinding binding) {
            if (recyclerViewWeakReference == null) {
                return false;
            }
            RecyclerView recyclerView = recyclerViewWeakReference.get();
            if (recyclerView == null) {
                return false;
            }
            recyclerView = (RecyclerView) binding.getRoot().getParent();
            if (recyclerView != null && !recyclerView.isComputingLayout()) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(binding.getRoot());
                if (childAdapterPosition == -1) {
                    return true;
                } else {
                    PagedListAdapter.this.notifyItemChanged(childAdapterPosition, PagedListAdapter.DB_PAYLOAD);
                    return false;
                }
            } else {
                return true;
            }
        }
    };

    private static ErrorViewBinder defaultViewBinder = new ErrorViewBinder();
    private LayoutInflater inflater;
    protected TypeProvider<Cell> provider = new TypeProvider<Cell>() {
        @Override
        public int getItemViewType(Cell cell) {
            return cell.getItemViewType();
        }
    };

    public PagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    public PagedListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }


    public TypeProvider getProvider() {
        return this.provider;
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerViewWeakReference = new WeakReference<>(recyclerView);
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerViewWeakReference.clear();
        this.recyclerViewWeakReference = null;
    }

    @NonNull
    @CallSuper
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (this.inflater == null) {
            this.inflater = LayoutInflater.from(parent.getContext());
        }

        ViewBinder binder = this.provider.getViewBinder(viewType);
        if (binder == null) {
            binder = defaultViewBinder;
        }

        assert this.inflater != null;

        return ((ViewBinder) binder).onCreateViewHolder(this.inflater, parent);
    }

    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        throw new IllegalArgumentException("just overridden to make final.");
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        ViewBinder binder = this.provider.getViewBinder(holder.getItemViewType());
        if (binder == null) {
            binder = defaultViewBinder;
        }

        holder.setItem(this.getItem(position));
        binder.onBindViewHolder(holder, payloads);
    }

    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewBinder binder = this.provider.getViewBinder(holder.getItemViewType());
        if (binder == null) {
            binder = defaultViewBinder;
        }

        binder.adapter = this;
        binder.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ViewBinder binder = this.provider.getViewBinder(holder.getItemViewType());
        if (binder == null) {
            binder = defaultViewBinder;
        }

        binder.onViewDetachedFromWindow(holder);
    }

    public int getItemViewType(int position) {
        return this.provider.getItemViewType(this.getItem(position));
    }


    @Override
    public OnRebindCallback<ViewDataBinding> provider() {
        return mOnRebindCallback;
    }
}
