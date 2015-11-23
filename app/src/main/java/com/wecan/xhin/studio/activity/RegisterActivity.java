package com.wecan.xhin.studio.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
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
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setSupportActionBar(binding.toolbar);
        api = App.from(this).createApi(Api.class);

        RxView.clickEvents(binding.btnRegister)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .filter(new SelectedFilter(binding.acpGroupName, R.string.group_no_selected))
                .filter(new SelectedFilter(binding.acpSex,R.string.sex_no_selected))
                .filter(new SelectedFilter(binding.acpPosition,R.string.position_no_selected))
                .filter(new InputFilter(binding.etName,R.string.name_no_input))
                .filter(new InputFilter(binding.etPhone,R.string.phone_no_input))
                .filter(new InputFilter(binding.etCode,R.string.code_no_input))
                .compose(new Observable.Transformer<ViewClickEvent, BaseData>() {
                    @Override
                    public Observable<BaseData> call(Observable<ViewClickEvent> viewClickEventObservable) {
                        return api.register(new RegisterBody(binding.acpPosition.getSelectedItemPosition()
                                , binding.acpGroupName.getSelectedItemPosition()
                                , binding.etPhone.getText().toString().trim()
                                , binding.acpSex.getSelectedItemPosition()
                                , binding.etName.getText().toString().trim()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showSimpleDialog(throwable.getMessage());
                    }
                })
                .subscribe(new Action1<BaseData>() {
                    @Override
                    public void call(BaseData user) {
                        showSimpleDialog(R.string.succeed);
                    }
                });
    }


}
