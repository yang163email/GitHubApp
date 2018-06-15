package com.yan.common

import org.junit.Test

/**
 *  @author : yan
 *  @date   : 2018/6/15 14:45
 *  @desc   : todo
 */
class PropertiesTest {

    @Test
    fun testProperties() {
        InfoProp().let {
            println(it.name)
            println(it.email)
            println(it.age)
            println(it.student)
            println(it.point)

            it.name = "jerry"
            it.email = "jerry@gmail.com"
            it.age = 4
        }
    }
}

class InfoProp : AbsProperties("info.properties") {
    var name: String by prop
    var email: String by prop
    var age: Int by prop
    var student: Boolean by prop
    var point: Float by prop
}