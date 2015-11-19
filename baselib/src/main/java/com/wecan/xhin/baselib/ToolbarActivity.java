package com.wecan.xhin.baselib;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public abstract class ToolbarActivity extends RxAppCompatActivity {

    protected Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    protected void showSnackBar(CharSequence message){
        Snackbar.make(toolbar,message,Snackbar.LENGTH_SHORT);
    }
}
