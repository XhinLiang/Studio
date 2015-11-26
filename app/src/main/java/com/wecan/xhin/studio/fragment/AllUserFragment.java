package com.wecan.xhin.studio.fragment;

import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.down.UsersData;

import rx.Observable;

/**
 * Created by xhinliang on 15-11-20.
 * xhinliang@gmail.com
 */
public class AllUserFragment extends UsersFragment {

    public static AllUserFragment newInstance() {
        return new AllUserFragment();
    }

    @Override
    protected Observable<UsersData> getUserApi(Api api) {
        return api.getAllUser();
    }
}
