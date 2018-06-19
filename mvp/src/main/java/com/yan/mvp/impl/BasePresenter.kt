package com.yan.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import com.yan.mvp.IMvpView
import com.yan.mvp.IPresenter

/**
 *  @author : yan
 *  @date   : 2018/6/15 16:04
 *  @desc   : 基类presenter
 */
abstract class BasePresenter<out V : IMvpView<BasePresenter<V>>> : IPresenter<V> {

    override lateinit var view: @UnsafeVariance V

    override fun onCreate(savedInstanceState: Bundle?) = Unit

    override fun onStart() = Unit

    override fun onResume() = Unit

    override fun onPause() = Unit

    override fun onStop() = Unit

    override fun onDestroy() = Unit

    override fun onSaveInstanceState(outState: Bundle) = Unit

    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit

    override fun onConfigurationChanged(newConfig: Configuration) = Unit
}