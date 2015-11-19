package com.wecan.xhin.studio.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xhinliang on 15-11-19.
 * xhinliang@gmail.com
 */
public class Book implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Book() {
    }

    protected Book(Parcel in) {
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
