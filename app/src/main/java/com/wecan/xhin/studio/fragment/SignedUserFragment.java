package com.wecan.xhin.studio.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.bean.down.BaseData;
import com.wecan.xhin.studio.bean.up.SignBody;
import com.wecan.xhin.studio.rx.RxNetworking;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-20.
 * xhinliang@gmail.com
 */
public class SignedUserFragment extends UsersFragment {

    public static final String KEY_USER = "user";

    private User user;
    private Observable<BaseData> observableSign;

    public static SignedUserFragment newInstance(User user) {
        SignedUserFragment fragment = new SignedUserFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setFabVisibility(final FloatingActionButton fab) {
        fab.setVisibility(View.VISIBLE);
        user = getArguments().getParcelable(KEY_USER);

        Observable.Transformer<BaseData, BaseData> networkingIndicator =
                RxNetworking.bindRefreshing(binding.srlRefresh);

        observableSign = Observable
                //defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行
                //defer操作符能够保证Observable的状态是最新的
                .defer(new Func0<Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call() {
                        if (user.status == User.VALUE_STATUS_SIGN)
                            return api.unsign(new SignBody(user.name));
                        return api.sign(new SignBody(user.name));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);

        setRxView(fab)
                .flatMap(new Func1<ViewClickEvent, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ViewClickEvent viewClickEvent) {
                        return observableSign;
                    }
                })
                .subscribe(new Action1<BaseData>() {
                    @Override
                    public void call(BaseData baseData) {
                        user.status = user.status == 0 ? 1 : 0;
                        fab.setImageResource(user.status == 1 ? R.drawable.defimgs : R.drawable.header);
                    }
                }, errorAction);
    }

    @Override
    protected Observable<List<User>> getUserApi(Api api) {
        return api.getSignedUser();
    }
}
