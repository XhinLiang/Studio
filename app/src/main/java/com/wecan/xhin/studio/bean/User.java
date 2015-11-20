package com.wecan.xhin.studio.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class User implements Parcelable {
    private String name;
    private String phone;
    private String avatar;
    private int position;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.avatar);
        dest.writeInt(this.position);
        dest.writeString(this.description);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.avatar = in.readString();
        this.position = in.readInt();
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
