package com.yan.github.model.account

import com.yan.github.utils.pref

/**
 *  @author : yan
 *  @date   : 2018/6/19 14:41
 *  @desc   : todo
 */
object AccountManager {

    var username by pref("")
    var passwd by pref("")
    var token by pref("")

    fun isLoggedIn(): Boolean = TODO()
}