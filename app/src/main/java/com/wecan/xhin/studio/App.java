
package com.wecan.xhin.studio;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.baselib.net.LoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class App extends Application {


    public static final String KEY_PREFERENCE_USER = "user";
    public static final String KEY_PREFERENCE_PHONE = "phone";

    private final HashMap<Class, Object> apis = new HashMap<>();

    private Retrofit retrofit;

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    //返回当前单例的静态方法,判断是否是当前的App调用
    public static App from(Context context) {
        Context application = context.getApplicationContext();
        if (application instanceof App)
            return (App) application;
        throw new IllegalArgumentException("Context must be from Studio");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, "AkVsXJ4RoWq1juPauOHe1OW5", "gFICBkjJSxlI5fHnvNyB7Lfj");

        OkHttpClient okHttpClient = new OkHttpClient();
        //OKHttp的使用
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header("version", BuildConfig.VERSION_NAME)
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
                .baseUrl(Api.BASE_URL)
                .build();
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

    public OkHttpClient getHttpClient() {
        return retrofit.client();
    }
}
