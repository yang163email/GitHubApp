package com.yan.common.ext

import android.util.Log
import java.io.File

/**
 *  @author : yan
 *  @date   : 2018/6/19 10:56
 *  @desc   : file扩展
 */

private const val TAG = "FileExt"

fun File.ensureDir(): Boolean {
    try {
        isDirectory.no {
            isFile.yes {
                delete()
            }
            return mkdirs()
        }
    } catch (e: Exception) {
        Log.w(TAG, e.message)
    }
    return false
}