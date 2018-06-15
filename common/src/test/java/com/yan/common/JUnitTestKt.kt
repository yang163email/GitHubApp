package com.yan.common

import org.junit.Test

/**
 *  @author : yan
 *  @date   : 2018/6/15 10:08
 *  @desc   : todo
 */
class JUnitTestKt {

    @Test
    fun test() {
        val result = true.yes {
            1
        }.otherwise {
            2
        }
        println(result)

        val result2 = true.no {
            1
        }.otherwise {
            2
        }
        println(result2)
    }
}