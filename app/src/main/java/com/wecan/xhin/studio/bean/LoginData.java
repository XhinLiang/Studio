package com.wecan.xhin.studio.bean;

import android.os.Parcel;

/**
 * Created by xhinliang on 15-11-19.
 * xhinliang@gmail.com
 */
public class LoginData extends BaseData {
    public User user;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.user, 0);
    }

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        super(in);
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        public LoginData createFromParcel(Parcel source) {
            return new LoginData(source);
        }

        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
}
