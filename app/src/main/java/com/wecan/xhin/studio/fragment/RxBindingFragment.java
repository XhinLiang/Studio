package com.wecan.xhin.studio.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class RxBindingFragment<V extends ViewDataBinding> extends RxFragment {

    protected V binding;

    @Nullable
    public V onCreateBinding(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        binding = onCreateBinding(inflater, container, savedInstanceState);
        return binding == null ? null : binding.getRoot();
    }

    protected Observable<ViewClickEvent> setRxView(View view) {
        return RxView.clickEvents(view)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS);
    }

}
