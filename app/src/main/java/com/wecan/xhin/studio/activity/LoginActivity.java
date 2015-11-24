package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityLoginBinding;
import com.wecan.xhin.studio.rx.RxNetworking;
import com.wecan.xhin.studio.util.PreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private Observable<User> observableConnect;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setSupportActionBar(binding.toolbar);
        api = App.from(this).createApi(Api.class);
        ProgressDialog pd = new ProgressDialog(this);

        Observable.Transformer<User, User> networkingIndicator = RxNetworking.bindConnecting(pd);

        observableConnect = Observable
                //defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行
                //defer操作符能够保证Observable的状态是最新的
                .defer(new Func0<Observable<User>>() {
                    @Override
                    public Observable<User> call() {
                        Map<String, String> map = new HashMap<>();
                        map.put(binding.etName.getText().toString(), binding.etPhone.getText().toString());
                        return api.login(map);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);

        setRxClick(binding.btnLogin)
                .filter(new InputFilter(binding.etName, R.string.name_no_input))
                .filter(new InputFilter(binding.etPhone, R.string.phone_no_input))
                .flatMap(new Func1<ViewClickEvent, Observable<User>>() {
                    @Override
                    public Observable<User> call(ViewClickEvent viewClickEvent) {
                        return observableConnect;
                    }
                })
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User meizhiData) {
                        PreferenceHelper.getInstance(LoginActivity.this)
                                .saveParam(App.KEY_PREFERENCE_USER, binding.etName.getText().toString());
                        PreferenceHelper.getInstance(LoginActivity.this)
                                .saveParam(App.KEY_PREFERENCE_PHONE, binding.etPhone.getText().toString());
                        Log.d(TAG, meizhiData.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage());
                    }
                });
    }
}

