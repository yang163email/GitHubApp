package com.yan.github.net.services

import com.yan.github.net.entities.AuthorizationReq
import com.yan.github.net.entities.AuthorizationRsp
import com.yan.github.net.retrofit
import com.yan.github.settings.Configs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:41
 *  @desc   : 认证api
 */
interface AuthApi {

    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(@Body req: AuthorizationReq,
                            @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint)
            : Observable<AuthorizationRsp>

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>
}

object AuthService: AuthApi by retrofit.create(AuthApi::class.java)