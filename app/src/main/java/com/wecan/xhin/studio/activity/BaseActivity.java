package com.wecan.xhin.studio.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wecan.xhin.studio.R;

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
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    protected void showSimpleDialog(int contentRes) {
        new AlertDialog.Builder(this)
                .setMessage(contentRes)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public class SpinnerFilter implements Func1<ViewClickEvent, Boolean> {
        AppCompatSpinner spinner;
        int messageRes;

        public SpinnerFilter(AppCompatSpinner spinner, int messageRes) {
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

    public class EditTextFilter implements Func1<ViewClickEvent, Boolean> {
        EditText editText;
        int messageRes;

        public EditTextFilter(EditText editText, int messageRes) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
