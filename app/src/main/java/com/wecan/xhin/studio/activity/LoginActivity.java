package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.umeng.update.UmengUpdateAgent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.baselib.rx.RxNetworking;
import com.wecan.xhin.baselib.util.PreferenceHelper;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityLoginBinding;

import java.io.IOException;

import retrofit.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

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

        binding.setName(PreferenceHelper.getInstance(this).getString(App.KEY_PREFERENCE_USER, getString(R.string.nothing)));
        binding.setPhone(PreferenceHelper.getInstance(this).getString(App.KEY_PREFERENCE_PHONE, getString(R.string.nothing)));

        observableConnect = Observable
                //defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行
                //defer操作符能够保证Observable的状态是最新的
                .defer(new Func0<Observable<User>>() {
                    @Override
                    public Observable<User> call() {
                        return api.login(binding.etName.getText().toString(), binding.etPhone.getText().toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);

        RxView.clickEvents(binding.btnLogin)
                .filter(new EditTextFilter(binding.etName, R.string.name_no_input))
                .filter(new EditTextFilter(binding.etPhone, R.string.phone_no_input))
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        login();
                    }
                });

        setRxClick(binding.btnRegister)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    }
                });

        setupUmengUpdate();
    }

    private void login() {
        observableConnect
                .compose(this.<User>bindToLifecycle())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        PreferenceHelper.getInstance(LoginActivity.this).saveParam(App.KEY_PREFERENCE_USER, binding.etName.getText().toString());
                        PreferenceHelper.getInstance(LoginActivity.this).saveParam(App.KEY_PREFERENCE_PHONE, binding.etPhone.getText().toString());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra(MainActivity.KEY_USER, user));
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //事实上在code != 200 的时候 , 可以获取响应的body.
                        if (throwable instanceof HttpException) {
                            try {
                                showSimpleDialog(R.string.login_fail, ((HttpException) throwable).response().errorBody().string());
                            } catch (IOException e) {
                                showSimpleDialog(R.string.login_fail, throwable.getMessage());
                            }
                            return;
                        }
                        showSimpleDialog(R.string.login_fail, throwable.getMessage());
                    }
                });
    }


    private void setupUmengUpdate() {
        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setDeltaUpdate(false);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
    }
}

