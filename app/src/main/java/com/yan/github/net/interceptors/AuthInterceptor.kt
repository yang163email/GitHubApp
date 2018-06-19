package com.yan.github.net.interceptors

import android.util.Base64
import com.yan.github.model.account.AccountManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  @author : yan
 *  @date   : 2018/6/19 14:37
 *  @desc   : 认证拦截器
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        return chain.proceed(original.newBuilder()
                .apply {
                    when {
                        original.url().pathSegments().contains("authorizations") -> {
                            //如果url包含authorizations,添加相应header
                            val userCredentials = AccountManager.username + ":" + AccountManager.passwd
                            val auth = "Basic " + String(Base64.encode(userCredentials.toByteArray(),
                                    Base64.DEFAULT)).trim()
                            header("Authorization", auth)
                        }
                        AccountManager.isLoggedIn() -> {
                            //如果已经登录，则添加token到header中
                            val auth = "Token " + AccountManager.token
                            header("Authorization", auth)
                        }
                        //否则移除
                        else -> removeHeader("Authorization")
                    }
                }
                .build())
    }
}