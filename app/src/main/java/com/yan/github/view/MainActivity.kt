package com.yan.github.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.yan.common.ext.no
import com.yan.common.ext.otherwise
import com.yan.github.R
import com.yan.github.model.account.AccountManager
import com.yan.github.model.account.OnAccountStateChangeListener
import com.yan.github.net.entities.User
import com.yan.github.utils.doOnLayoutAvailable
import com.yan.github.utils.loadWithGlide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast


/**
 *  @author : yan
 *  @date   : 2018/6/20 11:23
 *  @desc   : 主界面
 */
@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        initNavigationView()
        //添加登录监听器
        AccountManager.onAccountStateChangeListeners.add(this)
    }

    private fun initNavigationView() {
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
        initNavigationHeaderEvent()
    }

    private fun initNavigationHeaderEvent() {
        navigationView.doOnLayoutAvailable {
            navigationHeader.onClick {
                AccountManager.isLoggedIn().no {
                    startLoginActivity()
                }.otherwise {
                    AccountManager.logout()
                            .subscribe({
                                toast("注销成功")
                            }, {
                                it.printStackTrace()
                            })
                }
            }
        }
    }

    private fun updateNavigationView(user: User) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user.login
            emailView.text = user.email ?: ""
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = "请登录"
            emailView.text = ""
            avatarView.imageResource = R.drawable.ic_github
        }
    }

    override fun onLogin(user: User) {
        updateNavigationView(user)
    }

    override fun onLogout() {
        clearNavigationView()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }
}