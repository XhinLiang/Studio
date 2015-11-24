package com.wecan.xhin.studio.fragment;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by xhinliang on 15-11-24.
 * xhinliang@gmail.com
 */
public class BaseFragment extends RxFragment {

    protected Observable<ViewClickEvent> setRxView(View view) {
        return RxView.clickEvents(view)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS);
    }
}
