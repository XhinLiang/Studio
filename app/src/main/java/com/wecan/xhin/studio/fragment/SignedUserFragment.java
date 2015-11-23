package com.wecan.xhin.studio.fragment;

import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.down.UsersData;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-20.
 * xhinliang@gmail.com
 */
public class SignedUserFragment extends UsersFragment {

    public static SignedUserFragment newInstance() {
        return new SignedUserFragment();
    }

    @Override
    protected void initData() {
        Api api = App.from(getActivity()).createApi(Api.class);
        api.getSignedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<UsersData>bindToLifecycle())
                .subscribe(new Action1<UsersData>() {
                    @Override
                    public void call(UsersData usersData) {
                        users.addAll(usersData.data);
                    }
                });
    }
}
