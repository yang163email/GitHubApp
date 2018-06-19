package com.yan.common.ext

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *  @author : yan
 *  @date   : 2018/6/19 10:59
 *  @desc   : log扩展
 */
val loggerMap = HashMap<Class<*>, Logger>()

inline val <reified T> T.logger: Logger
    get() {
        return loggerMap[T::class.java] ?: LoggerFactory.getLogger(T::class.java).apply {
            loggerMap[T::class.java] = this
        }
    }
