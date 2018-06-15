package com.yan.mvp

/**
 *  @author : yan
 *  @date   : 2018/6/15 15:33
 *  @desc   : mvp---V与P接口定义
 */

interface IPresenter<out V : IMvpView<IPresenter<V>>> : ILifecycle {
    val view: V
}

interface IMvpView<out P : IPresenter<IMvpView<P>>> : ILifecycle {
    val presenter: P
}