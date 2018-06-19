package com.yan.github.net.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 *  @author : yan
 *  @date   : 2018/6/19 15:13
 *  @desc   : 添加accept header拦截器
 */
class AcceptInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        return chain.proceed(original.newBuilder()
                .apply {
                    header("accept", "application/vnd.github.v3.full+json, ${original.header("accept") ?: ""}")
                }
                .build())
    }
}