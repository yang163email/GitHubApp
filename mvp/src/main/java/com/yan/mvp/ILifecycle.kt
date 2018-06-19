package com.yan.mvp

import android.content.res.Configuration
import android.os.Bundle

/**
 *  @author : yan
 *  @date   : 2018/6/15 15:38
 *  @desc   : activity 生命周期接口
 */
interface ILifecycle {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(savedInstanceState: Bundle?)

    fun onConfigurationChanged(newConfig: Configuration)
}
