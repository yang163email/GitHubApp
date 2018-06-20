package com.yan.github.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.yan.common.ext.otherwise
import com.yan.common.ext.yes
import com.yan.github.R
import com.yan.github.presenter.LoginPresenter
import com.yan.github.utils.hideSoftInput
import com.yan.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity<LoginPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInBtn.onClick {
            presenter.checkUsername(username.text.trim().toString())
                    .yes {
                        presenter.checkPassword(password.text.trim().toString())
                                .yes {
                                    hideSoftInput()
                                    presenter.doLogin(username.text.trim().toString(),
                                            password.text.trim().toString())
                                }
                                .otherwise {
                                    showTips(password, "密码不合法")
                                }
                    }
                    .otherwise {
                        showTips(username, "用户名不合法")
                    }
        }
    }

    private fun showTips(editText: EditText, tips: String) {
        editText.requestFocus()
        editText.error = tips
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        login_form.animate()
                .setDuration(shortAnimTime.toLong())
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.animate()
                .setDuration(shortAnimTime.toLong())
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    fun onLoginStart() {
        showProgress(true)
    }

    fun onLoginSuccess() {
        toast("登录成功")
        showProgress(false)
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        toast("登录失败")
        showProgress(false)
    }

    fun onDataInit(name: String, pwd: String) {
        username.setText(name)
        password.setText(pwd)
    }
}
