package com.yan.github

import android.app.Application
import android.content.ContextWrapper

/**
 *  @author : yan
 *  @date   : 2018/6/15 11:24
 *  @desc   : todo
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

private lateinit var INSTANCE: Application

object AppContext: ContextWrapper(INSTANCE)
