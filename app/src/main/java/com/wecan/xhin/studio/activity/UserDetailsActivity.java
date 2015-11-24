package com.wecan.xhin.studio.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityUserDetailsBinding;

import rx.functions.Action1;

public class UserDetailsActivity extends BaseActivity {

    public static final String KEY_USER = "user";
    public static final String KEY_IS_CURRENT_USER = "is_current_user";

    public static final int VALUE_CURRENT_USER = 1;
    public static final int VALUE_NOT_CURRENT_USER = 0;

    private ActivityUserDetailsBinding binding;
    private RequestManager requestManager;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        User user = intent.getParcelableExtra(KEY_USER);
        binding.setUser(user);
        setupImage(binding.ivPicture, binding.getUser().imgurl);
        if (intent.getIntExtra(KEY_IS_CURRENT_USER, VALUE_NOT_CURRENT_USER) == VALUE_CURRENT_USER)
            setAsCurrentUser();
    }

    private void setAsCurrentUser() {
        setRxClick(binding.ivPicture)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        setRxClick(binding.mrlName)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        setRxClick(binding.mrlPhone)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        setRxClick(binding.mrlGroup)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        setRxClick(binding.mrlPosition)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

        setRxClick(binding.mrlDescription)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = Glide.with(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        setRxClick(binding.fab)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {

                    }
                });
    }

    private void setupImage(ImageView image, String imageUrl) {
        requestManager.load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }
}
