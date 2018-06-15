package com.yan.common

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *  @author : yan
 *  @date   : 2018/6/15 11:12
 *  @desc   : sharedPreferences代理类
 */
class Preference<T>(val context: Context, val name: String, val default: T, val prefName: String = "default")
    : ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun findPreference(name: String): T = with(prefs) {
        when (default) {
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }

    private fun putPreference(name: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Int -> putInt(name, value)
                is Long -> putLong(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                is String -> putString(name, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }.apply()
    }

}