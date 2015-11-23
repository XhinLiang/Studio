package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityLoginBinding;
import com.wecan.xhin.studio.util.PreferenceHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity {

    private ProgressDialog pd;
    private ActivityLoginBinding binding;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setSupportActionBar(binding.toolbar);
        api = App.from(this).createApi(Api.class);
        pd = new ProgressDialog(this);

        RxView.clickEvents(binding.btnLogin)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .filter(new InputFilter(binding.etName, R.string.name_no_input))
                .filter(new InputFilter(binding.etPhone, R.string.phone_no_input))
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .doOnNext(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        pd.show();
                    }
                })
                .compose(new Observable.Transformer<ViewClickEvent, User>() {
                    @Override
                    public Observable<User> call(Observable<ViewClickEvent> viewClickEventObservable) {
                        Map<String, String> map = new HashMap<>();
                        map.put(binding.etName.getText().toString(), binding.etPhone.getText().toString());
                        return api.login(map);
                    }
                })
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User loginData) {
                        pd.hide();
                        PreferenceHelper.getInstance(LoginActivity.this)
                                .saveParam(App.KEY_PREFERENCE_USER, binding.etName.getText().toString());
                        PreferenceHelper.getInstance(LoginActivity.this)
                                .saveParam(App.KEY_PREFERENCE_USER, binding.etPhone.getText().toString());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pd.hide();
                        showSimpleDialog(throwable.getMessage());
                    }
                })
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .putExtra(MainActivity.KEY_USER, user));
                        finish();
                    }
                });
    }
}

