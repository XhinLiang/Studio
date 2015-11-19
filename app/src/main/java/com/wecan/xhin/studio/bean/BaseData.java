/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wecan.xhin.studio.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by drakeet on 8/9/15.
 * drakeet
 */
public class BaseData implements Parcelable {
    public static final int VALUE_SUCCESS = 1;
    public static final int VALUE_FAIL = 0;

    public int code;
    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    public BaseData() {
    }

    protected BaseData(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

}
