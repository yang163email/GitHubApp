package com.yan.github.utils

import android.content.Context
import android.provider.Settings

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:18
 *  @desc   : 获取设备id
 */
val Context.deviceId: String
    get() = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
    )