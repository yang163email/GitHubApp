package com.yan.common.ext

/**
 *  @author : yan
 *  @date   : 2018/6/15 10:09
 *  @desc   : boolean扩展
 */
/**
 * @param T 因为WithData类有一个data字段，是只读类型，所以为out
 */
sealed class BooleanExt<out T>

/**
 * 此处泛型Nothing表示任意类型的子类
 */
object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T) =
        when {
            this -> {
                WithData(block())
            }
            else -> {
                Otherwise
            }
        }

inline fun <T> Boolean.no(block: () -> T) =
        when {
            this -> {
                Otherwise
            }
            else -> {
                WithData(block())
            }
        }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
        when (this) {
            is Otherwise -> block()
            is WithData -> data
        }