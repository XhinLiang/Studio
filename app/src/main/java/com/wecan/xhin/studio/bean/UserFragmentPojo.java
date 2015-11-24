package com.wecan.xhin.studio.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by xhinliang on 15-11-24.
 * xhinliang@gmail.com
 */
public class UserFragmentPojo extends BaseObservable {
    private boolean fabVisibility;
    private boolean status;

    public UserFragmentPojo(boolean fabVisibility, boolean status) {
        this.fabVisibility = fabVisibility;
        this.status = status;
    }

    public boolean isFabVisibility() {
        return fabVisibility;
    }

    public void setFabVisibility(boolean fabVisibility) {
        this.fabVisibility = fabVisibility;
    }

    @Bindable
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
