
package com.wecan.xhin.studio.api;


import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.bean.down.BaseData;
import com.wecan.xhin.studio.bean.down.UsersData;
import com.wecan.xhin.studio.bean.up.RegisterBody;
import com.wecan.xhin.studio.bean.up.SignBody;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;
import rx.Observable;

public interface Api {

    String BASE_URL = "http://121.42.209.19/RestfulApi/index.php/";

    @GET("api/users")
    Observable<User> login(@Query("name") String name, @Query("phone") String phone);

    @POST("api/users")
    Observable<BaseData> register(@Body RegisterBody registerUser);

    @PUT("api/users")
    Observable<User> updateUser(@Body User updateUser);

    @POST("api/sign")
    Observable<BaseData> sign(@Body SignBody user);

    @DELETE("api/sign")
    Observable<BaseData> unSign(@Query("name") String name);

    @GET("api/message_all")
    Observable<UsersData> getAllUser();

    @GET("api/message")
    Observable<UsersData> getSignedUser();


}
