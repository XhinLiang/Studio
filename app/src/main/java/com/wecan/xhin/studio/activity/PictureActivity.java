package com.wecan.xhin.studio.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.databinding.ActivityPictureBinding;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends BaseActivity {

    public static final String KEY_IMAGE_URL = "image_url";
    public static final String TRANSIT_PIC = "picture";

    private PhotoViewAttacher mPhotoViewAttacher;
    private ActivityPictureBinding binding;
    private RequestManager requestManager;

    protected void setAppBarAlpha(float alpha) {
        binding.appBar.setAlpha(alpha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
        setSupportActionBar(binding.toolbar);
        setHasHomeButton();

        requestManager = Glide.with(this);
        String imgUrl = getIntent().getStringExtra(KEY_IMAGE_URL);

        ViewCompat.setTransitionName(binding.picture, TRANSIT_PIC);
        setupImage(binding.picture, imgUrl);

        setAppBarAlpha(0.7f);
        setupPhotoAttacher();
    }

    private void setupImage(ImageView image, String imageUrl) {
        if (imageUrl == null || imageUrl.length() == 0)
            return;
        requestManager.load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.defimgs)
                .into(image);
    }

    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(binding.picture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoViewAttacher.cleanup();
    }
}
