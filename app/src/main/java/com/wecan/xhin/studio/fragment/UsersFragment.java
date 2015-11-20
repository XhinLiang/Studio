package com.wecan.xhin.studio.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecan.xhin.studio.activity.UserDetailsActivity;
import com.wecan.xhin.studio.adapter.UsersAdapter;
import com.wecan.xhin.studio.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class UsersFragment extends Fragment implements UsersAdapter.Listener {

    private static final String KEY_USERS = "user";
    private ObservableArrayList<User> users;

    public static UsersFragment newInstance(List<User> users) {
        UsersFragment fragment = new UsersFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_USERS, new ArrayList<>(users));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<User> arrayList = getArguments().getParcelableArrayList(KEY_USERS);
        RecyclerView recyclerView = new RecyclerView(getActivity());
        users = new ObservableArrayList<>();
        users.addAll(arrayList);
        recyclerView.setAdapter(new UsersAdapter(getActivity(), users, this));
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onUserItemClick(UsersAdapter.ViewHolder holder) {
        User user = users.get(holder.getAdapterPosition());
        startActivity(new Intent(getActivity(), UserDetailsActivity.class).putExtra(UserDetailsActivity.KEY_USER, user));
    }
}
