package com.yan.github.model.account

import com.google.gson.Gson
import com.yan.github.net.entities.AuthorizationReq
import com.yan.github.net.entities.AuthorizationRsp
import com.yan.github.net.entities.User
import com.yan.github.net.services.AuthService
import com.yan.github.net.services.UserService
import com.yan.github.utils.fromJson
import com.yan.github.utils.pref
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *  @author : yan
 *  @date   : 2018/6/19 14:41
 *  @desc   : todo
 */
object AccountManager {

    var authId by pref(-1)
    var username by pref("")
    var passwd by pref("")
    var token by pref("")

    private var userJson by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                //如果当前为null，并且json不为empty，直接解析json获取数据
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            //这里先将json赋值（包括清除数据）
            if (value == null) {
                userJson = ""
            } else {
                userJson = Gson().toJson(value)
            }
            field = value
        }

    fun isLoggedIn(): Boolean = TODO()

    fun login() =
            AuthService.createAuthorization(AuthorizationReq())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        if (it.token.isEmpty()) throw AccountException(it)
                    }
                    .retryWhen {
                        it.flatMap {
                            if (it is AccountException) {
                                AuthService.deleteAuthorization(it.authorizationRsp.id)
                            } else {
                                Observable.error(it)
                            }
                        }
                    }
                    .flatMap {
                        token = it.token
                        authId = it.id
                        UserService.getAuthenticateUser()
                    }

    fun logout() =
            AuthService.deleteAuthorization(authId)
                    .doOnNext {
                        if (it.isSuccessful) {
                            authId = -1
                            token = ""
                            currentUser = null
                        } else {
                            throw HttpException(it)
                        }
                    }

    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")
}