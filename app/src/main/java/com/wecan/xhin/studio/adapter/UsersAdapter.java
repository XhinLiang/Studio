package com.wecan.xhin.studio.adapter;

import android.content.Context;
import android.databinding.ObservableList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.RecyclerItemUserBinding;
import com.wecan.xhin.studio.rx.BindingRecyclerView;

public class UsersAdapter extends BindingRecyclerView.ListAdapter<User, UsersAdapter.ViewHolder> {

    private Listener listener;


    private RequestManager requestManager;


    public UsersAdapter(Context context, ObservableList<User> data, Listener listener, RequestManager requestManager) {
        super(context, data);
        this.listener = listener;
        this.requestManager = requestManager;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerItemUserBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User item = data.get(position);
        holder.binding.setUser(item);

        // execute the binding immediately to ensure
        // the original size of RatioImageView is set before layout
        holder.binding.executePendingBindings();

        setupImage(holder.binding.ivAvatar, item.imgurl);
        holder.itemView.setTag(item);
    }

    private void setupImage(ImageView image, String imageUrl) {
        if (imageUrl == null || imageUrl.length() == 0)
            return;
        requestManager.load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }

    //重载这个方法并设置 RecyclerView#setHasStableIds能大幅度提高性能
    @Override
    public long getItemId(int position) {
        return data.get(position).getName().hashCode();
    }

    //一种在ViewHolder里实现点击事件的方法,绑定当前Adapter的Activity实现这个接口即可
    public interface Listener {
        void onUserItemClick(ViewHolder holder);
    }

    public class ViewHolder extends BindingRecyclerView.ViewHolder<RecyclerItemUserBinding> {

        public ViewHolder(RecyclerItemUserBinding binding) {
            super(binding);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUserItemClick(ViewHolder.this);
                }
            });
        }
    }
}
