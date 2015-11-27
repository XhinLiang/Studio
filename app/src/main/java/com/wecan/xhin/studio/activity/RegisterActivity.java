package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.baselib.rx.RxNetworking;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.down.BaseData;
import com.wecan.xhin.studio.bean.up.RegisterBody;
import com.wecan.xhin.studio.databinding.ActivityRegisterBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private Api api;
    private Observable<BaseData> observableRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        api = App.from(this).createApi(Api.class);
        ProgressDialog pd = new ProgressDialog(this);

        Observable.Transformer<BaseData, BaseData> networkingIndicator = RxNetworking.bindConnecting(pd);

        observableRegister = Observable
                //defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行
                //defer操作符能够保证Observable的状态是最新的
                .defer(new Func0<Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call() {
                        return api.register(new RegisterBody(binding.acpPosition.getSelectedItemPosition()
                                , binding.acpGroupName.getSelectedItemPosition()
                                , binding.etPhone.getText().toString().trim()
                                , binding.acpSex.getSelectedItemPosition()
                                , binding.etName.getText().toString().trim()
                                , binding.etCode.getText().toString().trim()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);


        RxView.clickEvents(binding.btnRegister)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .filter(new SpinnerFilter(binding.acpGroupName, R.string.group_no_selected))
                .filter(new SpinnerFilter(binding.acpSex, R.string.sex_no_selected))
                .filter(new SpinnerFilter(binding.acpPosition, R.string.position_no_selected))
                .filter(new EditTextFilter(binding.etName, R.string.name_no_input))
                .filter(new EditTextFilter(binding.etPhone, R.string.phone_no_input))
                .filter(new EditTextFilter(binding.etCode, R.string.code_no_input))
                .flatMap(new Func1<ViewClickEvent, Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call(ViewClickEvent viewClickEvent) {
                        return observableRegister;
                    }
                })
                .retry()
                .subscribe(new Action1<BaseData>() {
                    @Override
                    public void call(BaseData user) {
                        showSimpleDialog(R.string.succeed);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showSimpleDialog(R.string.register_fail);
                    }
                });
    }
}
