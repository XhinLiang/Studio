package com.wecan.xhin.studio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.activity.UserDetailsActivity;
import com.wecan.xhin.studio.adapter.UsersAdapter;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.FragmentUsersBinding;
import com.wecan.xhin.studio.rx.RxEndlessRecyclerView;
import com.wecan.xhin.studio.rx.RxNetworking;

import java.util.List;

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
public abstract class UsersFragment extends BaseFragment implements UsersAdapter.Listener {

    protected ObservableArrayList<User> users;
    protected FragmentUsersBinding binding;
    protected Action1<Throwable> errorAction;
    protected Observable<List<User>> observableConnect;
    protected Api api;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        api = App.from(activity).createApi(Api.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, true);
        users = new ObservableArrayList<>();
        binding.rvUsers.setAdapter(new UsersAdapter(getActivity(), users, this, Glide.with(getActivity())));
        setFabVisibility(binding.fabSign);

        errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        Observable.Transformer<List<User>, List<User>> networkingIndicator =
                RxNetworking.bindRefreshing(binding.srlRefresh);

        observableConnect = Observable
                .defer(new Func0<Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call() {
                        return getUserApi(api);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);


        RxEndlessRecyclerView.reachesEnd(binding.rvUsers)
                .compose(this.<Integer>bindToLifecycle())
                .flatMap(new Func1<Integer, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(Integer integer) {
                        return observableConnect;
                    }
                })
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> newUsers) {
                        users.addAll(newUsers);
                    }
                }, errorAction);


        return binding.getRoot();
    }

    protected void setFabVisibility(FloatingActionButton fab) {
        fab.setVisibility(View.GONE);
    }

    protected abstract Observable<List<User>> getUserApi(Api api);

    @Override
    public void onUserItemClick(UsersAdapter.ViewHolder holder) {
        User user = users.get(holder.getAdapterPosition());
        startActivity(new Intent(getActivity(), UserDetailsActivity.class).putExtra(UserDetailsActivity.KEY_USER, user));
    }
}
