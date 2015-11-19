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


import com.wecan.xhin.studio.api.post.BodyLogin;
import com.wecan.xhin.studio.api.post.BodySign;
import com.wecan.xhin.studio.bean.BaseData;
import com.wecan.xhin.studio.bean.FellowData;
import com.wecan.xhin.studio.bean.LoginData;
import com.wecan.xhin.studio.bean.MeizhiData;
import com.wecan.xhin.studio.bean.User;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface Api {

    @GET("api/data/福利/" + 10 + "/{page}")
    Observable<MeizhiData> getMeizhiData(@Path("page") int page);

    @POST("api/data/福利/" + 10 + "/{page}")
    Observable<BaseData> register(@Body User user);

    @POST("api/data/福利/" + 10 + "/{page}")
    Observable<LoginData> login(@Body BodyLogin bodyLogin);

    @GET("api/data/福利/" + 10 + "/{page}")
    Observable<FellowData> getAllFellow();

    @GET("api/data/福利/" + 10 + "/{page}")
    Observable<FellowData> getInRoomFellow();

    @POST("api/data/福利/" + 10 + "/{page}")
    Observable<FellowData> sign(@Body BodySign bodySign);

    @POST("api/data/福利/" + 10 + "/{page}")
    Observable<BaseData> changeUser(@Body User user);


}
