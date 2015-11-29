package com.wecan.xhin.studio.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by xhinliang on 15-11-28.
 * xhinliang@gmail.com
 */
public class Test extends RxAppCompatActivity {

    int i = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Click");
        setContentView(button);
        RxView.clickEvents(button)
                .map(new Func1<ViewClickEvent, Integer>() {
                    @Override
                    public Integer call(ViewClickEvent viewClickEvent) {
                        --i;
                        return 5 / i;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("Test", "Error");

                    }
                })
                .retry()
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Test", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "Error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("Test", "Result :" + integer);
                    }
                });
    }
}