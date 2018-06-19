package com.yan.github.settings

import com.yan.common.ext.logger
import com.yan.github.AppContext
import com.yan.github.utils.deviceId

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:15
 *  @desc   : 网络相关配置
 */
object Configs {

    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "cccb7d70ba4fe6d4f62d"
        const val clientSecret = "30bea5fc021ed503bef21e23ce8e2b02d588ab6c"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also {
                logger.info("fingerPrint: $it")
            }
        }
    }
}