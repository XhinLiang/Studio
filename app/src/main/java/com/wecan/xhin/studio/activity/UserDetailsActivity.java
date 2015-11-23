package com.wecan.xhin.studio.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityUsetDetailsBinding;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class UserDetailsActivity extends RxAppCompatActivity {

    public static final String KEY_USER = "user";

    private ActivityUsetDetailsBinding binding;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        User user = intent.getParcelableExtra(KEY_USER);
        binding.setUser(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_uset_details);
        setSupportActionBar(binding.toolbar);

        RxView.clickEvents(binding.ivPicture)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        RxView.clickEvents(binding.fab)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });
    }
}
