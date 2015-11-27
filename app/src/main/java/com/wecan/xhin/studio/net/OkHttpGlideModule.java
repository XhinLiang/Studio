package com.wecan.xhin.studio.net;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.wecan.xhin.studio.App;

import java.io.InputStream;

public class OkHttpGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(App.from(context).getHttpClient()));
    }

}
