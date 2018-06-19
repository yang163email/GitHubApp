package com.yan.github.net.services

import com.yan.github.net.entities.User
import com.yan.github.net.retrofit
import retrofit2.http.GET
import rx.Observable

/**
 *  @author : yan
 *  @date   : 2018/6/19 14:32
 *  @desc   : user相关api
 */
interface UserApi {

    @GET("/user")
    fun getAuthenticateUser(): Observable<User>
}

object UserService: UserApi by retrofit.create(UserApi::class.java)