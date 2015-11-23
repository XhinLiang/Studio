/*
 * Mr.Mantou - On the importance of taste
 * Copyright (C) 2015  XiNGRZ <xxx@oxo.ooo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wecan.xhin.studio.api;


import com.wecan.xhin.studio.bean.MeizhiData;
import com.wecan.xhin.studio.bean.down.UsersData;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.bean.down.BaseData;
import com.wecan.xhin.studio.bean.up.RegisterBody;
import com.wecan.xhin.studio.bean.up.SignBody;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

public interface Api {

    String BASE_URL = "";

    @GET("api/data/福利/" + 10 + "/{page}")
    Observable<MeizhiData> getMeizhiData(@Path("page") int page);

    @GET("api/users")
    Observable<User> login(@QueryMap Map<String,String> user);

    @POST("api/users")
    Observable<BaseData> register(@Body RegisterBody registerUser);

    @PUT("api/users")
    Observable<User> updateUser(@Body User updateUser);

    @POST("api/sign")
    Observable<BaseData> sign(@Body SignBody user);

    @DELETE("api/sign")
    Observable<BaseData> unsign(@Body SignBody user);

    @GET("/api/message_all")
    Observable<UsersData> getAllUser();

    @GET("/api/message")
    Observable<UsersData> getSignedUser();


}
