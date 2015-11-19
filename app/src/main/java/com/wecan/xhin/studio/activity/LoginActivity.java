package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.api.Meizhi;
import com.wecan.xhin.studio.api.post.BodyLogin;
import com.wecan.xhin.studio.bean.BaseData;
import com.wecan.xhin.studio.bean.LoginData;
import com.wecan.xhin.studio.bean.MeizhiData;
import com.wecan.xhin.studio.databinding.ActivityLoginBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends RxAppCompatActivity {

    private final static String TAG = "LoginActivity";
    private ProgressDialog pd;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setSupportActionBar(binding.toolbar);
        api = App.from(this).createApi(Api.class);
        pd = new ProgressDialog(this);

        //****************************************
        api.getMeizhiData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<MeizhiData, Observable<Meizhi>>() {
                    @Override
                    public Observable<Meizhi> call(MeizhiData meizhis) {
                        return Observable.from(meizhis.results);
                    }
                })
                .subscribe(new Action1<Meizhi>() {
                    @Override
                    public void call(Meizhi meizhi) {
                        Log.d(TAG, meizhi.toString());
                    }
                });

        //****************************************

        RxTextView.textChangeEvents(binding.etName)
                .compose(this.<TextViewTextChangeEvent>bindToLifecycle())
                .throttleFirst(100, TimeUnit.MILLISECONDS) // 设置查询间隔为 100ms
                .subscribe(new Action1<TextViewTextChangeEvent>() {
                    @Override
                    public void call(TextViewTextChangeEvent o) {

                    }
                });

        RxView.clickEvents(binding.btnLogin)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .doOnNext(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        pd.show();
                    }
                })
                .compose(new Observable.Transformer<ViewClickEvent, LoginData>() {
                    @Override
                    public Observable<LoginData> call(Observable<ViewClickEvent> viewClickEventObservable) {
                        return api.login(new BodyLogin(binding.etName.getText().toString()
                                , binding.etPhone.getText().toString()));
                    }
                })
                .doOnNext(new Action1<LoginData>() {
                    @Override
                    public void call(LoginData loginData) {
                        pd.hide();
                    }
                })
                .subscribe(new Action1<LoginData>() {
                    @Override
                    public void call(LoginData loginData) {
                        if (loginData.code == BaseData.VALUE_SUCCESS)
                            startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("", loginData));
                    }
                });
    }
}

