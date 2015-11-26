package com.wecan.xhin.studio.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityUserDetailsBinding;

import rx.functions.Action1;

public class UserDetailsActivity extends BaseActivity {

    public static final String KEY_USER = "user";

    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = Glide.with(this);
        ActivityUserDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        final User user = getIntent().getParcelableExtra(KEY_USER);
        binding.setUser(user);
        setupImage(binding.ivPicture, binding.getUser().imgurl);

        setRxClick(binding.fab)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.phone));
                        //打电话有可能被拒绝
                        if (ActivityCompat.checkSelfPermission(UserDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                });

        setRxClick(binding.ivPicture)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        startActivity(new Intent(UserDetailsActivity.this, PictureActivity.class)
                                .putExtra(PictureActivity.KEY_IMAGE_URL, user.imgurl));
                    }
                });
    }

    private void setupImage(ImageView image, String imageUrl) {
        if (imageUrl == null || imageUrl.length() == 0)
            return;
        requestManager.load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.defimgs)
                .into(image);
    }

}
