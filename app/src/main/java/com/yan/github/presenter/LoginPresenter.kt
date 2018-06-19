package com.yan.github.presenter

import com.yan.github.model.account.AccountManager
import com.yan.github.view.LoginActivity
import com.yan.mvp.impl.BasePresenter

/**
 *  @author : yan
 *  @date   : 2018/6/19 16:03
 *  @desc   : 登录页面p层
 */
class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(name: String, passowrd: String) {
        AccountManager.username = name
        AccountManager.password = passowrd
        view.onLoginStart()
        AccountManager.login()
                .subscribe({
                    view.onLoginSuccess()
                }, {
                    view.onLoginError(it)
                })
    }

    fun checkUsername(name: String): Boolean {
        return true
    }

    fun checkPassword(password: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        view.onDataInit(AccountManager.username, AccountManager.password)
    }

}