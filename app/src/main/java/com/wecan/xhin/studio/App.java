/*
 * Mr.Mantou - On the importance of taste
 * Copyright (C) 2015  XiNGRZ <xxx@oxo.ooo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wecan.xhin.studio;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.wecan.xhin.studio.net.LoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class App extends Application {

    private final HashMap<Class, Object> apis = new HashMap<>();

    private OkHttpClient okHttpClient;

    private Retrofit retrofit;

    private static final String GANK_DATA = "http://gank.avosapps.com/";

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    //返回当前单例的静态方法,判断是否是当前的App调用
    public static App from(Context context) {
        Context application = context.getApplicationContext();
        if (application instanceof App) {
            return (App) application;
        } else {
            throw new IllegalArgumentException("context must be from MrApplication");
        }
    }

    //返回支持的语言,格式化返回
    private String buildAcceptLanguage() {
        Locale locale = Locale.getDefault();
        return String.format("%s-%s,%s;q=0.8,en-US;q=0.6,en;q=0.4",
                locale.getLanguage(), locale.getCountry(), locale.getLanguage());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient();

        //OKHttp的使用
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header("Accept-Language", buildAcceptLanguage())
                        .header("User-Agent", "")
                        .build());
            }
        });

        if (BuildConfig.DEBUG) {
            okHttpClient.networkInterceptors().add(new LoggingInterceptor());
        }

        //初始化Gson
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat(DATE_FORMAT_PATTERN)
                .create();

        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GANK_DATA)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    //返回Retrofit的API
    public <T> T createApi(Class<T> service) {
        if (!apis.containsKey(service)) {
            T instance = retrofit.create(service);
            apis.put(service, instance);
        }

        //noinspection unchecked
        return (T) apis.get(service);
    }

}
