/*
 * Mr.Mantou - On the importance of taste
 * Copyright (C) 2015  XiNGRZ <xxx@oxo.ooo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
