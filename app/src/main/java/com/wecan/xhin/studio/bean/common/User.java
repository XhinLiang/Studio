package com.wecan.xhin.studio.bean.common;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class User extends BaseObservable implements Parcelable {
    public int position;
    public int group_name;
    public int id;
    public int sex;
    public int status;

    public String phone;
    public String sign_date;


    public String name;
    public String imgurl;
    public String description;

    public static final String[] groups = {
            "组别", "前端", "后台", "移动", "产品", "设计", "YOU KNOW NOTHING"
    };

    public static final String[] positions = {
            "职位", "组员", "组长", "室长", "John Snow"
    };

    public static final String[] sexs = {
            "性别", "男", "女"
    };
    public static final int VALUE_STATUS_SIGN = 1;
    public static final int VALUE_STATUS_UNSIGN = 0;

    public String getName() {
        return name;
    }

    public static String getGroupName(int group) {
        return groups[group];
    }

    public static String getPositionName(int position) {
        return positions[position];
    }

    public static String getSexName(int sex) {
        return sexs[sex];
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
        dest.writeInt(this.status);
        dest.writeString(this.phone);
        dest.writeString(this.sign_date);
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
        this.status = in.readInt();
        this.phone = in.readString();
        this.sign_date = in.readString();
        this.name = in.readString();
        this.imgurl = in.readString();
        this.description = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
