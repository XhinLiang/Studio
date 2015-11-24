package com.wecan.xhin.studio.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.api.Meizhi;
import com.wecan.xhin.studio.bean.MeizhiData;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-19.
 * xhinliang@gmail.com
 */
public class Test extends BaseActivity {

    private static final String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        api();
        Button button = new Button(this);
        setContentView(button);
        setRxClick(button)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        Toast.makeText(Test.this, "Click", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void api() {
        Api api;
        api = App.from(this).createApi(Api.class);

        //****************************************
        api.getMeizhiData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<MeizhiData, Observable<Meizhi>>() {
                    @Override
                    public Observable<Meizhi> call(MeizhiData meizhis) {
                        return Observable.from(meizhis.results);
                    }
                })
                .subscribe(new Action1<Meizhi>() {
                    @Override
                    public void call(Meizhi meizhi) {
                        Log.d(TAG, meizhi.toString());
                    }
                });

        //****************************************
    }
}
