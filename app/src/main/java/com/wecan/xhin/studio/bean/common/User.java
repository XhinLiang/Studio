package com.wecan.xhin.studio.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class User implements Parcelable {
    public int position;	/*3*/
    public int group_name;	/*2*/
    public int id;	/*10*/
    public int sex;	/*1*/

    public String phone;	/*121*/
    public String sign_date;	/*2015-11-20 15:52:31*/
    public String status;	/*0*/
    public String name;	/*wer123*/
    public String imgurl;	/*www.123456.com*/
    public String description;

    public String getName() {
        return name;
    }

    public static String getGroupName(int group){
        return "SS";
    }

    public static String getPositionName(int position){
        return "SS";
    }

    public static String getSexName(int sex){
        return "SS";
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeInt(this.group_name);
        dest.writeInt(this.id);
        dest.writeInt(this.sex);
        dest.writeString(this.phone);
        dest.writeString(this.sign_date);
        dest.writeString(this.status);
        dest.writeString(this.name);
        dest.writeString(this.imgurl);
        dest.writeString(this.description);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.position = in.readInt();
        this.group_name = in.readInt();
        this.id = in.readInt();
        this.sex = in.readInt();
        this.phone = in.readString();
        this.sign_date = in.readString();
        this.status = in.readString();
        this.name = in.readString();
        this.imgurl = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
