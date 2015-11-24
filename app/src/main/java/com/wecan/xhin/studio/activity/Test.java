package com.wecan.xhin.studio.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.ViewClickEvent;

import rx.functions.Action1;

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
    }
}
