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

/**
 *  @author : yan
 *  @date   : 2018/6/19 14:41
 *  @desc   : 账户管理，包括登录、登出，存储用户数据等操作
 */
object AccountManager {

    var authId by pref(-1)
    var username by pref("")
    var password by pref("")
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

    val onAccountStateChangeListeners = arrayListOf<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach {
            it.onLogout()
        }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun login() =
            AuthService.createAuthorization(AuthorizationReq())
                    .doOnNext {
                        if (it.token.isEmpty()) throw AccountException(it)
                    }
                    .retryWhen {
                        it.flatMap {
                            if (it is AccountException) {
                                //如果是自己定义的异常，则删除掉认证信息
                                AuthService.deleteAuthorization(it.authorizationRsp.id)
                            } else {
                                //直接抛出
                                Observable.error(it)
                            }
                        }
                    }
                    .flatMap {
                        //此时将对应数据进行保存
                        token = it.token
                        authId = it.id
                        UserService.getAuthenticateUser()
                    }
                    .map {
                        currentUser = it
                        notifyLogin(it)
                    }

    fun logout() =
            AuthService.deleteAuthorization(authId)
                    .doOnNext {
                        if (it.isSuccessful) {
                            authId = -1
                            token = ""
                            currentUser = null
                            notifyLogout()
                        } else {
                            throw HttpException(it)
                        }
                    }

    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")
}

/**
 * 用户状态监听器
 */
interface OnAccountStateChangeListener {

    fun onLogin(user: User)

    fun onLogout()
}