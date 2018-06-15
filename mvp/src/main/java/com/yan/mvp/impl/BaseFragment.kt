package com.yan.mvp.impl

import android.support.v4.app.Fragment
import com.yan.mvp.IMvpView

/**
 *  @author : yan
 *  @date   : 2018/6/15 16:06
 *  @desc   : 基类fragment
 */
abstract class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : Fragment(), IMvpView<P> {

    override val presenter: P

    init {
        presenter = createPresenter()
        presenter.view = this
    }

    private fun createPresenter(): P {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}