/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wecan.xhin.studio.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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

    protected boolean mIsHidden = false;

    protected void setAppBarAlpha(float alpha) {
        binding.appBarLayout.setAlpha(alpha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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

    protected void hideOrShowToolbar() {
        binding.appBarLayout.animate()
                .translationY(mIsHidden ? 0 : -binding.appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }


    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(binding.picture);
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoViewAttacher.cleanup();
    }
}
