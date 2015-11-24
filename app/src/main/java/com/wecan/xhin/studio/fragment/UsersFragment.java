package com.wecan.xhin.studio.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.wecan.xhin.studio.activity.UserDetailsActivity;
import com.wecan.xhin.studio.adapter.UsersAdapter;
import com.wecan.xhin.studio.bean.common.User;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public abstract class UsersFragment extends RxFragment implements UsersAdapter.Listener {

    protected ObservableArrayList<User> users;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        users = new ObservableArrayList<>();
        recyclerView.setAdapter(new UsersAdapter(getActivity(), users, this, Glide.with(getActivity())));
        initData();
        return recyclerView;
    }

    protected abstract void initData();


    @Override
    public void onUserItemClick(UsersAdapter.ViewHolder holder) {
        User user = users.get(holder.getAdapterPosition());
        startActivity(new Intent(getActivity(), UserDetailsActivity.class).putExtra(UserDetailsActivity.KEY_USER, user));
    }
}
