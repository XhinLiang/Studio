package com.wecan.xhin.studio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.bean.Simple;
import com.wecan.xhin.studio.databinding.RecyclerItemSimpleBinding;
import com.wecan.xhin.studio.rx.BindingRecyclerView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class SimpleAdapter extends RecyclerView.Adapter<BindingRecyclerView.ViewHolder> {
    private List<Simple> libraries;
    private LayoutInflater inflater;
    private Listener listener;

    public SimpleAdapter(List<Simple> list, LayoutInflater inflater, Listener listener) {
        this.libraries = list;
        this.inflater = inflater;
        this.listener = listener;
    }

    @Override
    public BindingRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(RecyclerItemSimpleBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingRecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.binding.setSimple(libraries.get(position));
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public interface Listener {
        void onLibraryClick(ItemViewHolder holder);
    }

    public class ItemViewHolder extends BindingRecyclerView.ViewHolder<RecyclerItemSimpleBinding> {
        public ItemViewHolder(RecyclerItemSimpleBinding binding) {
            super(binding);
            RxView.clickEvents(itemView)
                    .subscribe(new Action1<ViewClickEvent>() {
                        @Override
                        public void call(ViewClickEvent viewClickEvent) {
                            listener.onLibraryClick(ItemViewHolder.this);
                        }
                    });
        }
    }
}