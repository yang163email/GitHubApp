package com.yan.github.net

import com.yan.common.ext.ensureDir
import com.yan.github.AppContext
import com.yan.github.net.interceptors.AcceptInterceptor
import com.yan.github.net.interceptors.AuthInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:35
 *  @desc   : retrofit相关配置
 */
private const val BASE_URL = "https://api.github.com"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply { ensureDir() }
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory2.createWithSchedulers(Schedulers.io(),
                    AndroidSchedulers.mainThread()))
            .client(
                    OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                            .addInterceptor(AcceptInterceptor())
                            .addInterceptor(AuthInterceptor())
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()
            )
            .baseUrl(BASE_URL)
            .build()
}