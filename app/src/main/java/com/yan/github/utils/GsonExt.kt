package com.yan.github.utils

import com.google.gson.Gson

/**
 *  @author : yan
 *  @date   : 2018/6/19 11:23
 *  @desc   : 简单gson扩展
 */
inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)