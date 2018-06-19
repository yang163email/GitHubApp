package com.yan.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.mvp.IMvpView
import com.yan.mvp.IPresenter
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.experimental.buildSequence
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

/**
 *  @author : yan
 *  @date   : 2018/6/19 10:13
 *  @desc   : 基类Activity
 */
abstract class BaseActivity<out P : BasePresenter<BaseActivity<P>>> : AppCompatActivity(), IMvpView<P> {

    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    /**
     * kt反射获取泛型，创建实例对象
     */
    private fun createPresenterKt(): P {
        buildSequence {
            var thisClass: KClass<*> = this@BaseActivity::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }

    /**
     * java反射获取泛型，创建实例
     */
    private fun createPresenter(): P {
        var thisClass = javaClass
        val genericSuperclass = thisClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            val clazz: Class<P> = genericSuperclass.actualTypeArguments[0] as Class<P>
            return clazz.newInstance()
        }
        throw IllegalArgumentException("can not create presenter instance")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        presenter.onConfigurationChanged(newConfig)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

    }
}