package com.yan.github.settings

import com.yan.common.ext.Preference
import com.yan.github.AppContext

/**
 *  @author : yan
 *  @date   : 2018/6/15 11:11
 *  @desc   : 可以直接通过sp代理获取对应存储值
 */
object Settings {

    var email: String by Preference(AppContext, "email", "")
    var password: String by Preference(AppContext, "password", "")
}