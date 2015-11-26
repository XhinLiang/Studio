package com.wecan.xhin.studio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.wecan.xhin.baselib.rx.RxNetworking;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.activity.UserDetailsActivity;
import com.wecan.xhin.studio.adapter.UsersAdapter;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.bean.down.UsersData;
import com.wecan.xhin.studio.databinding.FragmentUsersBinding;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public abstract class UsersFragment extends RxBindingFragment<FragmentUsersBinding>
        implements UsersAdapter.Listener {

    protected ObservableArrayList<User> users;
    protected Action1<Throwable> errorAction;
    protected Observable<UsersData> observableConnect;
    protected Api api;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        api = App.from(activity).createApi(Api.class);
    }

    @Nullable
    @Override
    public FragmentUsersBinding onCreateBinding(LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(final View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        users = new ObservableArrayList<>();
        binding.rvUsers.setAdapter(new UsersAdapter(getActivity(), users, this, Glide.with(getActivity())));
        Observable.Transformer<UsersData, UsersData> networkingIndicator =
                RxNetworking.bindRefreshing(binding.srlRefresh);

        errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        observableConnect = Observable
                .defer(new Func0<Observable<UsersData>>() {
                    @Override
                    public Observable<UsersData> call() {
                        return getUserApi(api);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);

        RxSwipeRefreshLayout.refreshes(binding.srlRefresh)
                .flatMap(new Func1<Void, Observable<UsersData>>() {
                    @Override
                    public Observable<UsersData> call(Void aVoid) {
                        return observableConnect;
                    }
                })
                .compose(this.<UsersData>bindToLifecycle())
                .subscribe(new Action1<UsersData>() {
                    @Override
                    public void call(UsersData usersData) {
                        users.clear();
                        users.addAll(usersData.data);
                    }
                });

        observableConnect.subscribe(new Action1<UsersData>() {
            @Override
            public void call(UsersData usersData) {
                users.clear();
                users.addAll(usersData.data);
            }
        });

    }

    protected abstract Observable<UsersData> getUserApi(Api api);

    @Override
    public void onUserItemClick(UsersAdapter.ViewHolder holder) {
        User user = users.get(holder.getAdapterPosition());
        startActivity(new Intent(getActivity(), UserDetailsActivity.class).putExtra(UserDetailsActivity.KEY_USER, user));
    }
}
