package com.wecan.xhin.studio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.bean.GitRepository;
import com.wecan.xhin.studio.databinding.RecyclerItemHeaderBinding;
import com.wecan.xhin.studio.databinding.RecyclerItemLibraryBinding;
import com.wecan.xhin.studio.rx.BindingRecyclerView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class LibrariesAdapter extends RecyclerView.Adapter<BindingRecyclerView.ViewHolder> {

    private static final int VALUE_TYPE_HEADER = 0;
    private static final int VALUE_TYPE_LIBRARY = 1;

    private List<GitRepository> libraries;
    private LayoutInflater inflater;
    private Listener listener;

    public LibrariesAdapter(List<GitRepository> list, LayoutInflater inflater, Listener listener) {
        this.libraries = list;
        this.inflater = inflater;
        this.listener = listener;
    }

    @Override
    public BindingRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new HeaderViewHolder(RecyclerItemHeaderBinding.inflate(inflater, parent, false));
        return new ItemViewHolder(RecyclerItemLibraryBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingRecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VALUE_TYPE_HEADER) {
            ((HeaderViewHolder) holder).binding.setName(libraries.get(position).name);
            return;
        }
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.binding.setName(libraries.get(position).toString());
    }

    @Override
    public int getItemViewType(int position) {
        return libraries.get(position).author == null ? VALUE_TYPE_HEADER : VALUE_TYPE_LIBRARY;
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }


    class HeaderViewHolder extends BindingRecyclerView.ViewHolder<RecyclerItemHeaderBinding> {
        public HeaderViewHolder(RecyclerItemHeaderBinding binding) {
            super(binding);
        }
    }

    public interface Listener {
        void onLibraryClick(ItemViewHolder holder);
    }

    public class ItemViewHolder extends BindingRecyclerView.ViewHolder<RecyclerItemLibraryBinding> {
        public ItemViewHolder(RecyclerItemLibraryBinding binding) {
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