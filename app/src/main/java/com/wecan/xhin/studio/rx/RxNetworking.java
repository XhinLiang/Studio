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

package com.wecan.xhin.studio.rx;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxNetworking {

    public static <T> Observable.Transformer<T, T> bindRefreshing(final SwipeRefreshLayout srl) {
        return new Observable.Transformer<T, T>() {
            //类似装饰者模式
            @Override
            public Observable<T> call(Observable<T> original) {
                return original.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        srl.post(new Runnable() {
                            @Override
                            public void run() {
                                srl.setRefreshing(true);
                            }
                        });
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        srl.post(new Runnable() {
                            @Override
                            public void run() {
                                srl.setRefreshing(false);
                            }
                        });
                    }
                });
            }
        };
    }

    public static <T> Observable.Transformer<T, T> bindConnecting(final ProgressDialog pd) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> original) {
                return original.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pd.show();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        pd.hide();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pd.hide();
                    }
                });
            }
        };
    }
}
