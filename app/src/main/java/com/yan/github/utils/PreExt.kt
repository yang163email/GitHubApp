package com.yan.github.utils

import com.yan.common.ext.Preference
import com.yan.github.AppContext
import kotlin.reflect.jvm.jvmName

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:32
 *  @desc   : sp扩展
 */
inline fun <reified R, T> R.pref(default: T) =
        Preference(AppContext, "", default, R::class.jvmName)