package com.program.mvvmdemo

import java.util.*

class UserModel {

    private val api by lazy {
        Api()
    }

    companion object{
        const val STATE_LOGIN_LOADING =0
        const val STATE_LOGIN_SUCCESS =1
        const val STATE_LOGIN_FAILED = 2
    }

    private val random = Random()

    fun doLogin(account: String, password: String, block: (Int) -> Unit) {
        block.invoke(STATE_LOGIN_LOADING)
        //开始调用登录api

        //有结果，此时为耗时操作
        var randomValue = random.nextInt(2)     //0,1
        if (randomValue == 0) {
           block.invoke(STATE_LOGIN_SUCCESS)
        } else {
            block.invoke(STATE_LOGIN_FAILED)
        }
    }

    fun checkUserState(account:String,bloc: (Int) -> Unit) {
        //0表示该账号已经注册
        //1         没有注册
        bloc.invoke(random.nextInt(2))

    }

    interface OnDoLoginStateChange {

        fun onLoading()
        fun onLoadingSuccess()
        fun onLoadingFailed()
    }
}