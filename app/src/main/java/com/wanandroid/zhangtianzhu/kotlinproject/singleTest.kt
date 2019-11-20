package com.wanandroid.zhangtianzhu.kotlinproject

/**
 * 饿汉式：
 */
object StateManagementHelper {
    fun init() {

    }
}

/**
 * 懒汉式
 */
class StateManagementHelperTwo private constructor() {
    companion object {
        private var instance: StateManagementHelperTwo? = null
            @Synchronized get() {
                if (field == null) {
                    instance = StateManagementHelperTwo()
                }
                return field
            }
    }

    fun init() {

    }
}

/**
 * 双重检查模式
 */
class StateManagementHelperThree private constructor() {
    companion object {
        val instance: StateManagementHelperThree
                by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { StateManagementHelperThree() }
    }
}

/**
 * 静态内部类
 */
class StateManagementHelperFour private constructor() {
    companion object {
        val instance = StateHelperHolder.holder
    }

    private object StateHelperHolder {
        val holder = StateManagementHelperFour()
    }
}
