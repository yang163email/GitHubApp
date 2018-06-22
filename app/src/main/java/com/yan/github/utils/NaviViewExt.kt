package com.yan.github.utils

import android.support.design.widget.NavigationView
import android.support.v4.view.ViewCompat
import android.view.View
import com.yan.common.ext.otherwise
import com.yan.common.ext.yes

/**
 *  @author : yan
 *  @date   : 2018/6/20 17:59
 *  @desc   : todo
 */
inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                removeOnLayoutChangeListener(this)
                block()
            }
        })
    }
}