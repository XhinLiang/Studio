package com.wecan.xhin.studio.activity;

import android.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.functions.Func1;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class BaseActivity extends RxAppCompatActivity {

    public rx.Observable<ViewClickEvent> setRxClick(View view) {
        return RxView.clickEvents(view)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .throttleFirst(500, TimeUnit.MILLISECONDS);
    }


    protected void showSimpleDialog(CharSequence content) {
        new AlertDialog.Builder(this)
                .setMessage(content)
                .create()
                .show();
    }

    protected void showSimpleDialog(int contentRes) {
        new AlertDialog.Builder(this)
                .setMessage(contentRes)
                .create()
                .show();
    }

    public class SelectedFilter implements Func1<ViewClickEvent, Boolean> {
        AppCompatSpinner spinner;
        int messageRes;

        public SelectedFilter(AppCompatSpinner spinner, int messageRes) {
            this.spinner = spinner;
            this.messageRes = messageRes;
        }

        @Override
        public Boolean call(ViewClickEvent viewClickEvent) {
            if (spinner.getSelectedItemPosition() == 0) {
                showSimpleDialog(messageRes);
                return false;
            }
            return true;
        }
    }

    public class InputFilter implements Func1<ViewClickEvent, Boolean> {
        EditText editText;
        int messageRes;

        public InputFilter(EditText editText, int messageRes) {
            this.editText = editText;
            this.messageRes = messageRes;
        }

        @Override
        public Boolean call(ViewClickEvent viewClickEvent) {
            if (editText.getText().toString().isEmpty()) {
                showSimpleDialog(messageRes);
                return false;
            }
            return true;
        }
    }
}
